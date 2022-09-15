package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.item.types.EntityInteractItem;
import es.angelillo15.mast.bukkit.api.item.types.ExecutableItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffCommandItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemClickEvent implements Listener {
    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if(e.getItem() == null) return;
        if(e.getItem().getType() == Material.AIR) return;
        if(!e.getItem().hasItemMeta()) return;

        if(MASTBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
            ItemMeta meta = e.getItem().getItemMeta();
            ArrayList<StaffItem> internalModules = MASTBukkitManager.getInternalModules();

            internalModules.forEach(staffItem -> {
                if (staffItem.getItem().getItemMeta().getDisplayName().equals(meta.getDisplayName())) {
                    if (!(staffItem instanceof EntityInteractItem)) {

                        if (staffItem instanceof ExecutableItem) {
                            ExecutableItem executableItem = (ExecutableItem) staffItem;
                            executableItem.click(player);
                        }

                        if (staffItem instanceof StaffCommandItem) {
                            StaffCommandItem staffCommandItem = (StaffCommandItem) staffItem;
                            staffCommandItem.click(player, staffCommandItem.getCommand());
                        }
                    }
                }
            });
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e){
        if(MASTBukkitManager.getInstance().containsStaffPlayer(e.getPlayer().getUniqueId())){
            if(e.getRightClicked() instanceof Player){
                if(e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR){
                    return;
                }
                MASTBukkitManager.getInternalModules().forEach(staffItem -> {
                    if (staffItem.getItem().getItemMeta().getDisplayName().equals(e.getPlayer().getItemInHand().getItemMeta().getDisplayName())) {
                        if(staffItem instanceof EntityInteractItem){
                            EntityInteractItem entityInteractItem = (EntityInteractItem) staffItem;
                            entityInteractItem.click(e.getPlayer(), (Player) e.getRightClicked());
                        }
                    }
                });
            }
        }

    }
}
