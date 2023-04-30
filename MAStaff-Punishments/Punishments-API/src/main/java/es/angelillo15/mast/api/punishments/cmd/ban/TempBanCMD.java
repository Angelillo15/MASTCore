package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.enums.ErrorTypes;
import es.angelillo15.mast.api.punishments.utils.BanUtils;
import es.angelillo15.mast.api.utils.NumberUtils;

@CommandData(
        name = "tempban",
        permission = "mastaff.punishments.tempban",
        aliases = {"tban"}
)
public class TempBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /tempban <player> <time> <reason>");
            return;
        }

        String target = args[0];

        StringBuilder reason = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        long time;

        try {
            time = System.currentTimeMillis() + NumberUtils.parseToMilis(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid time");
            return;
        }

        ErrorTypes error = BanUtils.ban(sender, BanUtils.getUserData(target), reason.toString(), time, false);

        if (error == ErrorTypes.NULL_DATA) {
            sender.sendMessage("Player not found");
            return;
        }

        if (error == ErrorTypes.PLAYER_ALREADY_PERM_BANNED) {
            sender.sendMessage("Player already perm banned");
            return;
        }

        if (error == ErrorTypes.SUCCESS) {
            sender.sendMessage("Player banned");
            return;
        }

        if (error == ErrorTypes.PLAYER_ALREADY_TEMP_BANNED) {
            sender.sendMessage("Player already temp banned");
            return;
        }

    }
}
