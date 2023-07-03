package es.angelillo15.mast.api.punishments.cmd.history;

import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.cmd.PunishCommand;

@CommandData(
        name = "history",
        permission = "mast.punishments.history",
        aliases = {"hist"}
)
public class HistoryCMD extends PunishCommand {
    @Override
    public void onCommand(IPunishPlayer sender, String label, String[] args) {
        if (args.length < 2) {
            return;
        }
    }
}
