package es.angelillo15.mast.api.punishments.cmd.warn;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.config.punishments.Config;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.managers.UserDataManager;
import es.angelillo15.mast.api.models.UserModel;
import es.angelillo15.mast.api.models.WarnModel;
import es.angelillo15.mast.api.utils.NumberUtils;

@CommandData(
        name = "warn",
        permission = "mast.warn"
)
public class WarnCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        new Thread(() -> {
            if (!(sender.hasPermission("mast.punishments"))) return;

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
            try {
                UserModel senderUser = UserDataManager.getUserData(sender.getName());
                UserModel targetUser = UserDataManager.getUserData(target);

                WarnModel warnModel = new WarnModel();
                warnModel.setWarnedBy(senderUser.getId());
                warnModel.setUser(targetUser.getId());
                warnModel.setReason(reason.toString());
                warnModel.setTime(System.currentTimeMillis());
                try {
                    warnModel.setUntil(System.currentTimeMillis() + NumberUtils.parseToMilis(Config.Warn.expireAfter()));
                } catch (Exception e) {
                    warnModel.setUntil(-1L);
                }

                PluginConnection.getStorm().save(warnModel);

            } catch (Exception e) {
                MAStaffInstance.getLogger().debug("Error while getting user data: " + e.getMessage());
                sender.sendMessage(Messages.Commands.playerNotFound(target));
            }


            sender.sendMessage(Messages.Commands.Warn.success(target, reason.toString(), sender.getName()));
        }).start();

    }
}
