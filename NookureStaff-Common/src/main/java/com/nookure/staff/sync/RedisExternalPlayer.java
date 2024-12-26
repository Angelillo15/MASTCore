package com.nookure.staff.sync;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.state.WrapperState;
import com.nookure.staff.api.util.ExceptionUtil;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public class RedisExternalPlayer implements PlayerWrapper {
  private final Logger logger;
  private final NookureStaff nookureStaff;

  @Inject
  public RedisExternalPlayer(@NotNull final Logger logger, @NotNull final NookureStaff nookureStaff) {
    this.logger = logger;
    this.nookureStaff = nookureStaff;
  }

  @Override
  public void sendPluginMessage(@NotNull String channel, byte @NotNull [] message) {
    logger.warning("Attempted to send plugin message to a player that is not on this server.");

    if (nookureStaff.isDebug()) {
      ExceptionUtil.warningCallStack(Thread.currentThread(), logger);
    }
  }

  @Override
  public @NotNull Set<String> getListeningPluginChannels() {
    logger.warning("Attempted to get listening plugin channels of a player that is not on this server.");

    if (nookureStaff.isDebug()) {
      ExceptionUtil.warningCallStack(Thread.currentThread(), logger);
    }

    return Set.of();
  }

  @Override
  public void teleport(@NotNull PlayerWrapper to) {
    // TODO: Implement teleportation
  }

  @Override
  public void kick(@NotNull String reason) {
    // TODO: Implement kicking
  }

  @Override
  public void kick(@NotNull Component reason) {
    // TODO: Implement kicking
  }

  @Override
  public PlayerModel getPlayerModel() {
    // TODO: Implement player model
    return null;
  }

  @Override
  public @NotNull WrapperState getState() {
    throw new UnsupportedOperationException("You cannot get the state of an external player.");
  }

  @Override
  public void sendMessage(@NotNull Component component) {
    // TODO: Implement sending messages
  }

  @Override
  public void sendActionbar(@NotNull Component component) {
    logger.warning("Attempted to send actionbar to a player that is not on this server.");

    if (nookureStaff.isDebug()) {
      ExceptionUtil.warningCallStack(Thread.currentThread(), logger);
    }
  }

  @Override
  public int getPing() {
    return -1;
  }

  @Override
  public @NotNull Component getDisplayName() {
    return null;
  }

  @Override
  public @NotNull String getName() {
    return "";
  }

  @Override
  public @NotNull UUID getUniqueId() {
    return null;
  }

  @Override
  public boolean hasPermission(@NotNull String permission) {
    // TODO: Implement permissions
    return false;
  }

  @Override
  public boolean isPlayer() {
    return true;
  }
}
