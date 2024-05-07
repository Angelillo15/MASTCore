package com.nookure.staff.paper.listener.player;

import com.google.inject.Inject;
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.util.Scheduler;
import io.ebean.Database;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class OnPlayerDataJoin implements Listener {
  @Inject
  private AtomicReference<Database> db;
  @Inject
  private Scheduler scheduler;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Optional<PlayerModel> optional = db.get().find(PlayerModel.class).where().eq("uuid", event.getPlayer().getUniqueId())
        .findOneOrEmpty();

    if (optional.isPresent()) {
      scheduler.async(() -> updatePlayer(optional.get(), event.getPlayer()).update());
      return;
    }

    PlayerModel player = updatePlayer(new PlayerModel(), event.getPlayer());
    player.setUuid(event.getPlayer().getUniqueId());
    player.setFirstLogin(Instant.now());

    try {
      player.setFirstIp(Objects.requireNonNull(event.getPlayer().getAddress()).getAddress().getHostAddress());
    } catch (NullPointerException e) {
      player.setFirstIp("0.0.0.0");
    }

    scheduler.async(player::save);
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
