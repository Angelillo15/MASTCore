package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class StaffInventoryClickEvent implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        if(e.getClickedInventory() == null) return;
        Player player = (Player) e.getWhoClicked();
        if(MASTBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
            if(e.getClickedInventory().getType() == InventoryType.PLAYER){
                e.setCancelled(true);
            }
        }
    }
}
