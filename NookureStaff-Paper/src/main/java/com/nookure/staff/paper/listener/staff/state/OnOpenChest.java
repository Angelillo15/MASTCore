package com.nookure.staff.paper.listener.staff.state;

import com.google.inject.Inject;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class OnOpenChest implements Listener {
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();

    if (playerWrapperManager.getStaffPlayer(player.getUniqueId()).isEmpty()) {
      return;
    }

    StaffPlayerWrapper playerWrapper = playerWrapperManager.getStaffPlayer(player.getUniqueId()).get();

    if (!playerWrapper.isInStaffMode()) {
      return;
    }

    if (
        event.getClickedBlock() == null ||
            event.getClickedBlock().getType().name().contains("CHEST") ||
            !(event.getClickedBlock().getState() instanceof Container container)
    ) {
      return;
    }

    if (!(container instanceof Chest chest)) {
      return;
    }

    Inventory cInv = Bukkit.createInventory(null, chest.getInventory().getSize(), chest.getInventory().getType().defaultTitle());
    cInv.setContents(chest.getInventory().getContents());

    player.openInventory(cInv);

    event.setCancelled(true);
  }

}
