package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MAStaffBukkitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropEvent implements Listener {
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player player = e.getPlayer();

        if(MAStaffBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
            Bukkit.getScheduler().runTaskAsynchronously(MAStaffBukkitManager.getInstance(), () -> {
                player.getInventory().clear();
                MAStaffBukkitManager.getInternalModules().forEach(staffItem -> {
                    staffItem.set(player);
                });
            });
            e.setCancelled(true);
        }

    }
}
