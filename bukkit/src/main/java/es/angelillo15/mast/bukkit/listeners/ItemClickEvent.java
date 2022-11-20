package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.item.items.CommandInteractionItem;
import es.angelillo15.mast.bukkit.api.item.items.ThruItem;
import es.angelillo15.mast.bukkit.api.item.items.VanishItem;
import es.angelillo15.mast.bukkit.api.item.types.EntityInteractItem;
import es.angelillo15.mast.bukkit.api.item.types.ExecutableItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffCommandItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemClickEvent implements Listener {
    private static boolean clicked = false;

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent e){
        Player player = e.getPlayer();





        if(MASTBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
            if(!e.getPlayer().hasPermission("mast.break")){
                e.setCancelled(true);
            }
            if(e.getItem() == null) return;
            if(e.getItem().getType() == Material.AIR) return;
            if(!e.getItem().hasItemMeta()) return;

            ItemMeta meta = e.getItem().getItemMeta();
            ArrayList<StaffItem> internalModules = MASTBukkitManager.getInternalModules();
            if(!clicked){
                clicked = true;
                e.setCancelled(true);
                return;
            }
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
            clicked = false;
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

                if(!clicked){
                    clicked = true;
                    e.setCancelled(true);
                    return;
                }
                MASTBukkitManager.getInternalModules().forEach(staffItem -> {
                    if (staffItem.getItem().getItemMeta().getDisplayName().equals(e.getPlayer().getItemInHand().getItemMeta().getDisplayName())) {
                        if(staffItem instanceof EntityInteractItem){
                            EntityInteractItem entityInteractItem = (EntityInteractItem) staffItem;
                            entityInteractItem.click(e.getPlayer(), (Player) e.getRightClicked());
                        }

                        if(staffItem instanceof CommandInteractionItem){
                            CommandInteractionItem commandInteractionItem = (CommandInteractionItem) staffItem;
                            commandInteractionItem.click(e.getPlayer(), (Player) e.getRightClicked());
                        }

                    }
                });
                clicked = false;
            }
        }

    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent e){
        if(MASTBukkitManager.getInstance().containsStaffPlayer(e.getPlayer().getUniqueId())){
            if(e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR){
                return;
            }

            if(!clicked){
                clicked = true;
                return;
            }
            MASTBukkitManager.getInternalModules().forEach(staffItem -> {

                if (staffItem.getItem().getItemMeta().getDisplayName().equals(e.getPlayer().getItemInHand().getItemMeta().getDisplayName())) {
                    if(staffItem instanceof ThruItem){
                        ThruItem thruItem = (ThruItem) staffItem;
                        if(e.getClickedBlock() == null){
                            return;
                        }
                        thruItem.click(e.getPlayer(), e.getClickedBlock().getLocation());
                    }

                    if(staffItem instanceof VanishItem){
                        VanishItem vanishItem = (VanishItem) staffItem;
                        vanishItem.click(e.getPlayer());
                    }
                }
            });
            clicked = false;
        }
    }
}
