package com.nookure.staff.paper.listener.staff.items;

import com.nookure.staff.api.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnPlayerInventoryClick implements Listener {
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (event.getInventory().getHolder(false) instanceof Player) {
       Player player = (Player) event.getWhoClicked();

       event.setCancelled(!player.hasPermission(Permissions.STAFF_INVSEE_MODIFY));
    }
  }
}
