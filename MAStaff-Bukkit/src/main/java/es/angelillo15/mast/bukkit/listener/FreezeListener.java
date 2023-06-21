package es.angelillo15.mast.bukkit.listener;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.event.bukkit.freeze.FreezeMessageEvent;
import es.angelillo15.mast.api.managers.FreezeManager;
import es.angelillo15.mast.bukkit.config.Messages;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {
    @EventHandler
    public void onFreezeEvent(PlayerMoveEvent event) {
        if(FreezeManager.isFrozen(event.getPlayer())) {
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
                Location loc = event.getFrom();

                PaperLib.teleportAsync(event.getPlayer(), loc.setDirection(event.getTo().getDirection()));
            }
        }
    }

    @EventHandler
    public void onFreezeMessage(FreezeMessageEvent e){
        Player player = e.getPlayer();
        if (player == null  || !player.isOnline()) {
            FreezeManager.removePlayer(player);
            return;
        }

        for (String message : Messages.spamMessage()) {
            if (message == null) continue;
            TextUtils.colorize(message, player);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(FreezeManager.isFrozen(event.getPlayer())){
            event.setCancelled(true);
        }
    }
}
