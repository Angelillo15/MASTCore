package es.angelillo15.mast.bungee.cmd;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.managers.LegacyUserDataManager;
import es.angelillo15.mast.api.models.UserModel;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.config.Messages;
import java.util.UUID;
import net.md_5.bungee.api.ProxyServer;

@CommandData(
    name = "info",
    description = "Shows information about a player",
    usage = "/info <player>/<uuid>",
    permission = "mast.admin.info")
public class InfoCMD extends Command {
  @Override
  public void onCommand(CommandSender sender, String label, String[] args) {
    new Thread(
            () -> {
              if (args.length < 1) {
                sender.sendMessage("/info <player>/<uuid>");
                return;
              }
              UserModel userModel = null;
              boolean isOnline;
              boolean isUUID = args[0].length() == 36;

              if (isUUID) {
                try {
                  userModel = LegacyUserDataManager.getUserData(UUID.fromString(args[0]));
                  isOnline = ProxyServer.getInstance().getPlayer(UUID.fromString(args[0])) != null;
                } catch (Exception e) {
                  sender.sendMessage(TextUtils.simpleColorize(Messages.getInfoUserNotFound()));
                  return;
                }
              } else {
                userModel = LegacyUserDataManager.getUserData(args[0]);
                isOnline = ProxyServer.getInstance().getPlayer(args[0]) != null;
              }

              if (userModel == null) {
                sender.sendMessage(
                    es.angelillo15.mast.bungee.utils.TextUtils.colorize(
                        Messages.getInfoUserNotFound().replace("{player}", args[0])));
                return;
              }

              for (String s : Messages.getInfoMessage()) {
                sender.sendMessage(
                    s.replace("{player}", userModel.getUsername())
                        .replace("{uuid}", userModel.getUUID())
                        .replace("{reg_ip}", userModel.getRegIp())
                        .replace("{last_ip}", userModel.getLastIp())
                        .replace(
                            "{reg_date}",
                            TextUtils.formatDate(userModel.getFirstLogin(), Config.dateFormat()))
                        .replace(
                            "{last_date}",
                            TextUtils.formatDate(userModel.getLastLogin(), Config.dateFormat()))
                        .replace("{online}", isOnline ? "Yes" : "No"));
              }
            })
        .start();
  }
}
