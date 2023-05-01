package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.punishments.config.Messages;
import es.angelillo15.mast.api.punishments.data.DataManager;
import es.angelillo15.mast.api.punishments.enums.ErrorTypes;
import es.angelillo15.mast.api.punishments.events.EventManager;
import es.angelillo15.mast.api.punishments.utils.BanUtils;

import java.util.UUID;

@CommandData(
        name = "ban",
        description = "Ban a player",
        usage = "/ban <player> <reason>",
        permission = "mast.punishments.ban",
        aliases = {"b"}
)
public class BanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        new Thread(() -> {
            if (args.length < 1) {
                sender.sendMessage(Messages.Commands.Ban.usage());
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
            UserData data = BanUtils.getUserData(target);
            ErrorTypes result = BanUtils.ban(sender, data , reason.toString(), 0, false);

            if (result == ErrorTypes.NULL_DATA) {
                sender.sendMessage(Messages.Commands.playerNotFound(target));
                return;
            }

            if (result == ErrorTypes.PLAYER_ALREADY_PERM_BANNED) {
                sender.sendMessage(Messages.Commands.playerAlreadyBanned(target));
                return;
            }

            if (result == ErrorTypes.SUCCESS) {
                sender.sendMessage(Messages.Commands.Ban.success(target, reason.toString()));
                try {
                    EventManager.getEventManager().sendPlayerBannedEvent(DataManager.getDataManager().getBan(UUID.fromString(data.getUUID())), sender);
                } catch (Exception e) {
                    EventManager.getEventManager().sendPlayerBannedEvent(DataManager.getDataManager().getBan(target), sender);
                }


                return;
            }

            sender.sendMessage("An error ocurred");
        }).start();
    }
}
