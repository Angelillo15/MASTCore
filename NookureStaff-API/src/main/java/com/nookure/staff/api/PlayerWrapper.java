package com.nookure.staff.api;

import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.model.PlayerModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Set;

public interface PlayerWrapper extends Serializable, CommandSender {
  /**
   * Send a plugin message to the proxy.
   *
   * @param channel the channel to send the message to
   * @param message the message to send
   */
  void sendPluginMessage(@NotNull String channel, byte @NotNull [] message);

  /**
   * Gets a set containing all the plugin channels
   * that this player is listening to.
   *
   * @return a set containing all the plugin channels
   */
  @NotNull
  Set<String> getListeningPluginChannels();

  /**
   * Teleport the player to another player.
   *
   * @param to the player to teleport to
   */
  void teleport(@NotNull PlayerWrapper to);

  /**
   * Get the player's model.
   *
   * @throws IllegalStateException if the player's model feature is disabled
   * @return the player's model
   */
  PlayerModel getPlayerModel();
}
