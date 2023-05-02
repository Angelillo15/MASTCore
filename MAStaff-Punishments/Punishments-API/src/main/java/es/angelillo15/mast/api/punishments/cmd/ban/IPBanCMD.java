package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;
import es.angelillo15.mast.api.config.punishments.Messages;

@CommandData(
        name = "ipban",
        permission = "mastaff.punishments.ipban",
        usage = "/ipban <ip> <reason>",
        description = "Ban an IP"
)
public class IPBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        IPunishPlayer punishPlayer = PunishPlayersManager.getPlayer(sender.getUniqueId());

        if (args.length < 1) {
            sender.sendMessage(Messages.Commands.IpBan.usage());
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
            sender.sendMessage(Messages.Commands.playerNotFound(target));
            return;
        }

        new Thread(() -> {
            punishPlayer.ban(args[0], reason.toString(), true);
            sender.sendMessage(Messages.Commands.IpBan.success(target, reason.toString(), sender.getName()));
        }).start();
    }
}
