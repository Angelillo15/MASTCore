package es.angelillo15.mast.bukkit.cmd;

import com.google.inject.Inject;
import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.StaffCommand;
import com.nookure.mast.api.cmd.sender.CommandSender;
import com.nookure.mast.api.manager.FreezeManager;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.config.bukkit.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "freeze-chat",
    aliases = {"freezec"},
    permission = "mast.freeze"
)
public class FreezeChat extends StaffCommand {
  @Inject
  private FreezeManager freezeManager;
  @Inject
  private IServerUtils serverUtils;

  @Override
  public void onStaffCommand(@NotNull IStaffPlayer sender, @NotNull String label, @NotNull String @NotNull [] args) {
    if (args.length < 2) {
      sender.sendMessage(Messages.FREEZE_CHAT_USAGE());
    }

    Player target = Bukkit.getPlayer(args[0]);

    if (target == null) {
      sender.sendMessage(Messages.GET_NO_PLAYER_FOUND_MESSAGE());
      return;
    }

    if (!freezeManager.isFrozen(target)) {
      sender.sendMessage(Messages.FREEZE_CHAT_NOT_FROZEN());
      return;
    }

    String message = String.join(" ", args)
        .replaceFirst(args[0], "")
        .replaceFirst(" ", "");

    String finalMessage = Messages.FREEZE_STAFF_CHAT_FORMAT()
        .replace("{player}", target.getName())
        .replace("{message}", message);

    serverUtils.broadcastMessage(finalMessage, "mast.freeze");

    if (target.hasPermission("mast.freeze")) {
      return;
    }

    target.sendMessage(finalMessage);
  }

  @NotNull
  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
    return freezeManager.getFrozenPlayers().stream().map(OfflinePlayer::getName).toList();
  }
}
