package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.item.StaffItem;
import es.angelillo15.mast.bukkit.utils.items.InternalModules;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemClickEvent implements Listener {
    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if(MASTBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
            ItemMeta meta = e.getItem().getItemMeta();
            ArrayList<StaffItem> internalModules = InternalModules.getInternalModules(player);
            internalModules.forEach(staffItem -> {
                if (staffItem.getItem().getItemMeta().getDisplayName().equals(meta.getDisplayName())){
                    staffItem.click();
                }
            });
        }
    }
}
