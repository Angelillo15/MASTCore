package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

@CommandData(
        name = "ipban",
        permission = "mastaff.punishments.ipban",
        usage = "/ipban <ip> <reason>",
        description = "Ban an IP"
)
public class IPBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {

    }
}
