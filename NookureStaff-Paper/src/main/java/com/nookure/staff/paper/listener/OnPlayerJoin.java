package com.nookure.staff.paper.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.state.PinState;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.paper.PaperPlayerWrapper;
import com.nookure.staff.paper.StaffPaperPlayerWrapper;
import io.ebean.Database;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class OnPlayerJoin implements Listener {
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  @Inject
  private AtomicReference<Database> db;
  @Inject
  private Injector injector;
  @Inject
  private Logger logger;
  @Inject
  private Scheduler scheduler;
  @Inject
  private ConfigurationContainer<BukkitConfig> config;

  @EventHandler(
      priority = EventPriority.LOWEST
  )
  public void onPlayerJoin(PlayerJoinEvent event) {
    logger.debug("Player %s has joined the server", event.getPlayer().getName());
    logger.debug("Creating player wrapper for %s", event.getPlayer().getName());
    scheduler.async(() -> {
      if (event.getPlayer().hasPermission(Permissions.STAFF_PERMISSION)) {
        StaffPaperPlayerWrapper playerWrapper = StaffPaperPlayerWrapper.Builder
            .create(injector)
            .setPlayer(event.getPlayer())
            .setModel(getPlayerModel(event.getPlayer()))
            .addState(PinState.class)
            .build();

        playerWrapperManager.addPlayerWrapper(event.getPlayer(), playerWrapper, true);

        logger.debug("Player %s has staff permission, adding staff player wrapper", event.getPlayer().getName());
        return;
      }

      PaperPlayerWrapper playerWrapper = PaperPlayerWrapper.Builder
          .create(injector)
          .setPlayer(event.getPlayer())
          .setModel(getPlayerModel(event.getPlayer()))
          .build();

      playerWrapperManager.addPlayerWrapper(event.getPlayer(), playerWrapper);

      logger.debug("Player %s does not have staff permission, adding player wrapper", event.getPlayer().getName());
    });
  }

  private PlayerModel getPlayerModel(Player p) {
    if (!config.get().modules.isPlayerData()) return null;

    Optional<PlayerModel> optional = db.get().find(PlayerModel.class).where().eq("uuid", p.getUniqueId())
        .findOneOrEmpty();

    if (optional.isPresent()) {
      PlayerModel m = updatePlayer(optional.get(), p);
      m.update();

      return m;
    }

    PlayerModel player = updatePlayer(new PlayerModel(), p);
    player.setUuid(p.getUniqueId());
    player.setFirstLogin(Instant.now());

    try {
      player.setFirstIp(Objects.requireNonNull(p.getAddress()).getAddress().getHostAddress());
    } catch (NullPointerException e) {
      player.setFirstIp("0.0.0.0");
    }

    player.save();

    return player;
  }


  private PlayerModel updatePlayer(PlayerModel player, Player bukkitPlayer) {
    player.setName(bukkitPlayer.getName());

    try {
      player.setLastIp(Objects.requireNonNull(bukkitPlayer.getAddress()).getAddress().getHostAddress());
    } catch (NullPointerException e) {
      player.setLastIp("0.0.0.0");
    }

    player.setLastLogin(Instant.now());
    return player;
  }
}
