package es.angelillo15.mast.api.punishments.cmd;

import com.nookure.mast.api.cmd.Command;
import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.exceptions.user.PlayerNotOnlineException;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;

@CommandData(
    name = "kick",
    permission = "mast.punishments.kick",
    aliases = {"kickplayer", "kickp"})
public class KickCMD extends Command {
  @Override
  public void onCommand(CommandSender sender, String label, String[] args) {
    if (!(sender.hasPermission("mast.punishments"))) return;

    IPunishPlayer punishPlayer = PunishPlayersManager.getPlayer(sender.getUniqueId());

    if (args.length < 1) {
      sender.sendMessage(Messages.Commands.Kick.usage());
      return;
    }

    StringBuilder reason = new StringBuilder();

    for (int i = 1; i < args.length; i++) {
      reason.append(args[i]).append(" ");
    }

    if (reason.toString().isEmpty()) {
      reason.append(Messages.Default.defaultKickReason());
    }

    String target = args[0];
    try {
      punishPlayer.kick(target, reason.toString());
    } catch (PlayerNotOnlineException e) {
      sender.sendMessage(Messages.Commands.playerNotOnline(target));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
