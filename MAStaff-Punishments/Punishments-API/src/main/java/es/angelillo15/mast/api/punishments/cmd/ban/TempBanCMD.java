package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

@CommandData(
        name = "tempban",
        permission = "mastaff.punishments.tempban",
        aliases = {"tban"}
)
public class TempBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {

    }
}
