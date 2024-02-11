package com.nookure.staff.paper.listener.staff;

import com.google.inject.Inject;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class OnStaffLeave implements Listener {
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;

  @EventHandler(ignoreCancelled = true)
  public void onStaffLeave(PlayerQuitEvent event) {
    Optional<StaffPlayerWrapper> optional = playerWrapperManager.getStaffPlayer(event.getPlayer().getUniqueId());

    if (optional.isEmpty()) {
      return;
    }

    StaffPlayerWrapper wrapper = optional.get();

    if (wrapper.isInStaffMode()) {
      wrapper.clearInventory();
      wrapper.restoreInventory();
      wrapper.disablePlayerPerks();
    }
  }
}
