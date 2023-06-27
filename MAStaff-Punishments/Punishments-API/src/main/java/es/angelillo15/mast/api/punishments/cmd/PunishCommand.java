package es.angelillo15.mast.api.punishments.cmd;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;

public abstract class PunishCommand extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender.hasPermission("mast.punishments"))) return;

        IPunishPlayer punishPlayer = PunishPlayersManager.getPlayer(sender.getUniqueId());

        if (punishPlayer == null) {
            return;
        }

        onCommand(punishPlayer, label, args);
    }

    public abstract void onCommand(IPunishPlayer sender, String label, String[] args);
}
