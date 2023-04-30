package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.enums.ErrorTypes;
import es.angelillo15.mast.api.punishments.utils.BanUtils;

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
            if (args.length < 2) {
                sender.sendMessage("Usage: /ban <player> <reason>");
                return;
            }


            String target = args[0];

            StringBuilder reason = new StringBuilder();

            for (int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }

            if (target == null) {
                sender.sendMessage("Player not found");
                return;
            }

            ErrorTypes result = BanUtils.ban(sender, BanUtils.getUserData(target), reason.toString(), 0, false);

            if (result == ErrorTypes.NULL_DATA) {
                sender.sendMessage("Player not found");
                return;
            }

            if (result == ErrorTypes.PLAYER_ALREADY_PERM_BANNED) {
                sender.sendMessage("Player already perm banned");
                return;
            }

            if (result == ErrorTypes.SUCCESS) {
                sender.sendMessage("Player banned");
                return;
            }

            sender.sendMessage("An error ocurred");
        }).start();
    }
}
