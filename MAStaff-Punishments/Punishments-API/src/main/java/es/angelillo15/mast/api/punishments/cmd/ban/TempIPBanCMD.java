package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

import java.util.UUID;

@CommandData(
        name = "tempipban",
        permission = "mast.ban.tempipban",
        aliases = {"tempipban", "tempipb"},
        usage = "&cUsage: &7/tempipban <player> <time> <reason>",
        description = "Temporarily IP ban a player"
)
public class TempIPBanCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {

    }
}
