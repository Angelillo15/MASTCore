package es.angelillo15.mast.api.punishments.cmd.history;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

@CommandData(
        name = "history",
        permission = "mast.punishments.history",
        aliases = {"hist"}
)
public class HistoryCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender.hasPermission("mast.punishments"))) return;

    }
}
