package es.angelillo15.mast.bungee.cmd;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.chat.api.chat.TextComponent;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.config.Messages;
import net.md_5.bungee.api.ProxyServer;

import java.util.UUID;

@CommandData(
        name = "info",
        description = "Shows information about a player",
        usage = "/info <player>/<uuid>",
        permission = "mast.admin.info"
)
public class InfoCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        new Thread(() -> {
            if (args.length < 1) {
                sender.sendMessage("/info <player>/<uuid>");
                return;
            }
            UserData userData = null;
            boolean isOnline;
            boolean isUUID = args[0].length() == 36;

            if (isUUID) {
                try {
                    userData = DataManager.getDataManager().getUserData(UUID.fromString(args[0]));
                    isOnline = ProxyServer.getInstance().getPlayer(UUID.fromString(args[0])) != null;
                } catch (Exception e) {
                    sender.sendMessage(TextUtils.simpleColorize(Messages.getInfoUserNotFound()));
                    return;
                }
            } else {
                userData = DataManager.getDataManager().getUserData(args[0]);
                isOnline = ProxyServer.getInstance().getPlayer(args[0]) != null;
            }

            if (userData == null) {
                sender.sendMessage(es.angelillo15.mast.bungee.utils.TextUtils.colorize(Messages.getInfoUserNotFound()));
                return;
            }

            for (String s : Messages.getInfoMessage()) {
                sender.sendMessage(s
                        .replace("{player}", userData.getUsername())
                        .replace("{uuid}", userData.getUUID())
                        .replace("{reg_ip}", userData.getRegIP())
                        .replace("{last_ip}", userData.getLastIP())
                        .replace("{reg_date}", TextUtils.formatDate(userData.getFirstLogin(),
                                Config.dateFormat())
                        )
                        .replace("{last_date}", TextUtils.formatDate(userData.getLastLogin(),
                                Config.dateFormat())
                        ).replace("{online}", isOnline ? "Yes" : "No")
                );
            }

        }).start();


    }
}
