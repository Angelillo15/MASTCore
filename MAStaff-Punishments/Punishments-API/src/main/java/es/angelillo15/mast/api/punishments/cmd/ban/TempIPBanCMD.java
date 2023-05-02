package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;
import es.angelillo15.mast.api.config.punishments.Config;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.utils.NumberUtils;

@CommandData(
        name = "tempipban",
        permission = "mast.ban.tempipban",
        aliases = {"tempipban", "tempipb"},
        usage = "&cUsage: &7/tempipban <player> <time> <reason>",
        description = "Temporarily IP ban a player"
)
public class TempIPBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        IPunishPlayer punishPlayer = PunishPlayersManager.getPlayer(sender.getUniqueId());

        if (args.length < 2) {
            sender.sendMessage(Messages.Commands.TempIpBan.usage());
            return;
        }

        String target = args[0];

        StringBuilder reason = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        if (reason.toString().isEmpty()) {
            reason.append(Messages.Default.defaultBanReason());
        }

        long time;

        try {
            time = System.currentTimeMillis() + NumberUtils.parseToMilis(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid time");
            return;
        }
        new Thread(() -> {
            punishPlayer.ban(target, reason.toString(), time, true);
            sender.sendMessage(Messages.Commands.TempIpBan.success(
                    target,
                    TextUtils.formatDate(time, Config.dateFormat()),
                    reason.toString(),
                    sender.getName())
            );
        }).start();
    }
}
