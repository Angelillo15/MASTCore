package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;
import es.angelillo15.mast.api.config.punishments.Messages;

@CommandData(
        name = "unban",
        description = "Unban a player",
        usage = "/unban <player>",
        permission = "mast.punishments.unban",
        aliases = {"ub"}
)
public class UnBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender.hasPermission("mast.punishments"))) return;

        IPunishPlayer punishPlayer = PunishPlayersManager.getPlayer(sender.getUniqueId());

        if (args.length < 1) {
            sender.sendMessage(Messages.Commands.Unban.usage());
            return;
        }

        StringBuilder reason = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        if (reason.toString().isEmpty()) {
            reason.append(Messages.Default.defaultUnbanReason());
        }

        String target = args[0];
        new Thread(() -> {
            try {
                punishPlayer.unban(target, reason.toString());
            } catch (Exception e) {
                MAStaffInstance.getLogger().debug("Error while unbanning player " + target + ": " + e.getMessage());
                return;
            }
            sender.sendMessage(Messages.Commands.Unban.success(
                    target,
                    reason.toString(),
                    sender.getName()
            ));
        }).start();
    }
}
