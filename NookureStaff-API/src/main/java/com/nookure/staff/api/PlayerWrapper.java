package com.nookure.staff.api;

import com.nookure.staff.api.command.sender.CommandSender;
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
  @NotNull Set<String> getListeningPluginChannels();
}
