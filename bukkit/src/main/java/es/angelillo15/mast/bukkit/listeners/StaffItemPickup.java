package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MAStaffBukkitManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class StaffItemPickup implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(MAStaffBukkitManager.getInstance().containsStaffPlayer(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
        }
    }

}
