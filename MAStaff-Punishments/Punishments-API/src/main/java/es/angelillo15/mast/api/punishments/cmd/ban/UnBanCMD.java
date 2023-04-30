package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.punishments.cache.BanCache;
import es.angelillo15.mast.api.punishments.config.Messages;
import es.angelillo15.mast.api.punishments.utils.BanUtils;

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
        new Thread(() -> {
            if (args.length < 1) {
                sender.sendMessage(Messages.Commands.Unban.usage());
                return;
            }

            String target = args[0];

            UserData data = BanUtils.getUserData(target);

            if (data == null) {
                sender.sendMessage(Messages.Commands.playerNotFound(target));
                return;
            }

            if (!BanUtils.isBanned(data.getUUID())) {
                sender.sendMessage(Messages.Commands.playerNotBanned(target));
                return;
            }

            BanUtils.unban(data.getUUID());
            BanCache.clearCache();

            sender.sendMessage(Messages.Commands.Unban.success(data.getUsername()));
        }).start();
    }
}
