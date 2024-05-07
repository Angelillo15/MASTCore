package com.nookure.staff.paper.listener.freeze;

import com.google.inject.Inject;
import com.nookure.staff.api.manager.FreezeManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnFreezePlayerMove implements Listener {
  @Inject
  private FreezeManager freezeManager;

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    if (!freezeManager.isFrozen(event.getPlayer())) return;

    if (event.getFrom().getX() != event.getTo().getX()
        || event.getFrom().getY() != event.getTo().getY()
        || event.getFrom().getZ() != event.getTo().getZ()) {
      Location loc = event.getFrom();

      event.getPlayer().teleportAsync(loc.setDirection(event.getTo().getDirection()));
    }
  }
}
