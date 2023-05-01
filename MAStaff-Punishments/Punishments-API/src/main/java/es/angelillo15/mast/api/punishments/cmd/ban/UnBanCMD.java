package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

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

    }
}
