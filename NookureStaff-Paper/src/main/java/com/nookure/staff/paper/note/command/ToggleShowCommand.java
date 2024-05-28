package com.nookure.staff.paper.note.command;

import com.google.inject.Inject;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.service.UserNoteService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "toggle-show",
    description = "Toggle show on join for a note",
    permission = Permissions.STAFF_NOTES_EDIT
)
public class ToggleShowCommand extends Command {
  @Inject
  private UserNoteService userNoteService;

  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    if (args.isEmpty()) {
      sender.sendMiniMessage("Usage: /note toggle-show <note id>");
      return;
    }

    long id;

    try {
      id = Long.parseLong(args.get(0));
    } catch (NumberFormatException e) {
      sender.sendMiniMessage("Invalid note id");
      return;
    }

    userNoteService.toggleShowOnJoin(sender, id);
  }
}
