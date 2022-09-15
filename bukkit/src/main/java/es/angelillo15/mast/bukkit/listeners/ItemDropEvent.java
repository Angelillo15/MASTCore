package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropEvent implements Listener {
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player player = e.getPlayer();

        if(MASTBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
            Bukkit.getScheduler().runTaskAsynchronously(MASTBukkitManager.getInstance(), () -> {
                player.getInventory().clear();
                MASTBukkitManager.getInternalModules().forEach(staffItem -> {
                    staffItem.set(player);
                });
            });
            e.setCancelled(true);
        }

    }
}
