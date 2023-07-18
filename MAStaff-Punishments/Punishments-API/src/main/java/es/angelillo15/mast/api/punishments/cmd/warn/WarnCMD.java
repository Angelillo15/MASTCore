package es.angelillo15.mast.api.punishments.cmd.warn;

import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.cmd.PunishCommand;

@CommandData(
        name = "warn",
        permission = "mast.warn"
)
public class WarnCMD extends PunishCommand {
    @Override
    public void onCommand(IPunishPlayer sender, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Messages.Commands.Warn.usage());
            return;
        }

        String target = args[0];

        StringBuilder reason = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        if (reason.toString().isEmpty()) {
            reason.append(Messages.Default.defaultWarnReason());
        }

        sender.warn(target, reason.toString());
    }
}
