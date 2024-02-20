package com.nookure.staff.paper.util;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.staff.api.event.server.BroadcastMessage;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.util.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Singleton
public class PaperServerUtils extends ServerUtils {
  @Inject
  private EventMessenger eventMessenger;
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;

  @Override
  public boolean isOnline(@NotNull UUID uuid) {
    return Bukkit.getPlayer(uuid) != null;
  }

  @Override
  public boolean isOnline(@NotNull String name) {
    return Bukkit.getPlayer(name) != null;
  }

  @Override
  public void broadcast(@NotNull String message, @NotNull String permission) {
    playerWrapperManager.stream().findFirst().ifPresent(player -> eventMessenger.publish(player, new BroadcastMessage(message, permission)));
  }
}
