package com.nookure.staff.paper.messaging;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.messaging.Channels;
import com.nookure.staff.api.messaging.EventMessenger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class BackendMessageMessenger extends EventMessenger implements PluginMessageListener {
  @Inject
  private EventManager eventManager;
  @Inject
  private Logger logger;

  @Override
  public void prepare() {
    // Nothing to do here
  }

  @Override
  public void publish(@NotNull PlayerWrapper sender, byte @NotNull [] data) {
    sender.sendPluginMessage(Channels.EVENTS, data);
  }

  @Override
  public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
    if (!channel.equals(Channels.EVENTS)) {
      return;
    }
    try {
      logger.debug("Received event from plugin message");
      decodeEvent(message).ifPresent(eventManager::fireEvent);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
