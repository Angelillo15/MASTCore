package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffManager;
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
  @Inject
  private StaffManager staffManager;

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();

    if (!staffManager.isStaffPlayer(player)) {
      return;
    }

    IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

    assert staffPlayer != null;

    if (!staffPlayer.isStaffMode()) {
      return;
    }

    if (event.getClickedBlock() == null) {
      return;
    }

    if (!event.getClickedBlock().getType().name().contains("CHEST")) {
      return;
    }

    if (!(event.getClickedBlock().getState() instanceof Container container)) {
      return;
    }

    if (!(container instanceof Chest chest)) {
      return;
    }

    Inventory cInv = Bukkit.createInventory(null, chest.getInventory().getSize(), chest.getInventory().getType().name());
    cInv.setContents(chest.getInventory().getContents());
    player.openInventory(cInv);
    event.setCancelled(true);
  }
}
