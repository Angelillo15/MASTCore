package com.nookure.staff.paper.note.command;

import com.google.inject.Inject;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.service.UserNoteService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "add",
    description = "Add a note to a user",
    permission = Permissions.STAFF_NOTES_ADD
)
public class AddNoteCommand extends Command {
  @Inject
  private UserNoteService userNoteService;

  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    if (args.size() < 3) {
      // todo add usage messages for all commands
      sender.sendMiniMessage("Bad");
    }

    String target = args.get(0);
    boolean showOnJoin;
    boolean showOnlyToAdmins = false;
    String note;

    try {
      showOnJoin = Boolean.parseBoolean(args.get(1));
    } catch (IllegalArgumentException e) {
      sender.sendMiniMessage("Invalid value for show on join");
      return;
    }

    if (args.get(args.size() - 1).equalsIgnoreCase("true")) {
      showOnlyToAdmins = true;
      note = String.join(" ", args.subList(2, args.size() - 1));
    } else {
      note = String.join(" ", args.subList(2, args.size()));
    }

    userNoteService.addNote(sender, target, note, showOnJoin, showOnlyToAdmins);
  }

  @Override
  public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    return switch (args.size()) {
      case 1 -> Bukkit.getOnlinePlayers().stream()
          .map(Player::getName)
          .filter(name -> name.startsWith(args.get(0) == null ? "" : args.get(0)))
          .toList();
      case 2 -> List.of("<show on join>", "true", "false");
      default -> {
        if (sender.hasPermission(Permissions.STAFF_NOTES_ADMIN)) {
          yield List.of("<note> | <show only to admins>", "true", "false");
        } else {
          yield List.of("<note>");
        }
      }
    };
  }
}
