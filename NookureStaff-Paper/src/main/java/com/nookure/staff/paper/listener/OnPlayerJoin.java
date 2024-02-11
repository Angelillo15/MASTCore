package com.nookure.staff.paper.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.paper.PaperPlayerWrapper;
import com.nookure.staff.paper.StaffPaperPlayerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  @Inject
  private Injector injector;
  @Inject
  private Logger logger;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    logger.debug("Player %s has joined the server", event.getPlayer().getName());
    logger.debug("Creating player wrapper for %s", event.getPlayer().getName());
    if (event.getPlayer().hasPermission(Permissions.STAFF_PERMISSION)) {
      StaffPaperPlayerWrapper playerWrapper = StaffPaperPlayerWrapper.Builder
          .create(injector)
          .setPlayer(event.getPlayer())
          .build();

      playerWrapperManager.addPlayerWrapper(event.getPlayer(), playerWrapper, true);

      logger.debug("Player %s has staff permission, adding staff player wrapper", event.getPlayer().getName());
      return;
    }

    PaperPlayerWrapper playerWrapper = PaperPlayerWrapper.Builder
        .create(injector)
        .setPlayer(event.getPlayer())
        .build();

    playerWrapperManager.addPlayerWrapper(event.getPlayer(), playerWrapper);

    logger.debug("Player %s does not have staff permission, adding player wrapper", event.getPlayer().getName());
  }
}
