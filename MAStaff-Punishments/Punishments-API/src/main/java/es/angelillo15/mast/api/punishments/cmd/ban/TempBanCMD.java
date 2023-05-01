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
import es.angelillo15.mast.api.utils.NumberUtils;

import java.util.UUID;

@CommandData(
        name = "tempban",
        permission = "mastaff.punishments.tempban",
        aliases = {"tban"}
)
public class TempBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Messages.Commands.TempBan.usage());
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

        UserData data = BanUtils.getUserData(target);

        ErrorTypes error = BanUtils.ban(sender, data, reason.toString(), time, false);

        if (error == ErrorTypes.NULL_DATA) {
            sender.sendMessage(Messages.Commands.playerNotFound(target));
            return;
        }

        if (error == ErrorTypes.PLAYER_ALREADY_PERM_BANNED) {
            sender.sendMessage(Messages.Commands.playerAlreadyBanned(target));
            return;
        }

        if (error == ErrorTypes.PLAYER_ALREADY_TEMP_BANNED) {
            sender.sendMessage("Player already temp banned");
            return;
        }

        if (error == ErrorTypes.SUCCESS) {
            try {
                EventManager.getEventManager().sendPlayerBannedEvent(DataManager.getDataManager().getBan(UUID.fromString(data.getUUID())), sender);
            } catch (Exception e) {
                EventManager.getEventManager().sendPlayerBannedEvent(DataManager.getDataManager().getBan(target), sender);
            }
            sender.sendMessage(Messages.Commands.TempBan.success(target, args[1], reason.toString(), sender.getName()));
        }
    }
}
