package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.punishments.cmd.PunishCommand;

@CommandData(
        name = "ban",
        description = "Ban a player",
        usage = "/ban <player> <reason>",
        permission = "mast.punishments.ban",
        aliases = {"b"}
)
public class BanCMD extends PunishCommand {
    @Override
    public void onCommand(IPunishPlayer punishPlayer, String label, String[] args) {
        if (args.length < 1) {
            punishPlayer.sendMessage(Messages.Commands.Ban.usage());
            return;
        }

        String target = args[0];

        StringBuilder reason = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        if (reason.toString().isEmpty()) {
            reason.append(Messages.Default.defaultBanReason());
        }

        if (target == null) {
            punishPlayer.sendMessage(Messages.Commands.playerNotFound(target));
            return;
        }

        punishPlayer.ban(args[0], reason.toString());
        punishPlayer.sendMessage(Messages.Commands.Ban.success(target, reason.toString(), punishPlayer.getName()));
    }
}
