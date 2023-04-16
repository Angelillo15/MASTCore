package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class OnOpenChest implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!StaffPlayersManagers.isStaffPlayer(player)) {
            return;
        }

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if (!staffPlayer.isStaffMode()) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        if (!event.getClickedBlock().getType().name().contains("CHEST")) {
            return;
        }

        Container container = (Container) event.getClickedBlock().getState();

        if (container instanceof Chest) {
            Chest chest = (Chest) container;
            Inventory cInv = Bukkit.createInventory(null, chest.getInventory().getSize(), chest.getInventory().getType().name());

            player.openInventory(cInv);

            event.setCancelled(true);
        }
    }
}
