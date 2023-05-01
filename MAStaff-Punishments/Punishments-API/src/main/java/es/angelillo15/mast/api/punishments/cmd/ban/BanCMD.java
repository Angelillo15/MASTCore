package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

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

    }
}
