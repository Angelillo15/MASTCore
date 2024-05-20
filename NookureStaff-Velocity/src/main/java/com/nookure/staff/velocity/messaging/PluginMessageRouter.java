package com.nookure.staff.velocity.messaging;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.messaging.Channels;
import com.nookure.staff.api.messaging.EventMessenger;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

public class PluginMessageRouter {
  public static final MinecraftChannelIdentifier EVENTS = MinecraftChannelIdentifier.from(Channels.EVENTS);
  @Inject
  private ProxyServer server;
  @Inject
  private Logger logger;
  @Inject
  private EventMessenger messenger;

  @Subscribe
  public void onPluginMessageFromPlayer(PluginMessageEvent event) {
    if (event.getIdentifier() != EVENTS) {
      return;
    }

    logger.debug("Routing plugin message to all servers.");

    messenger.decodeEvent(event.getData());

    server.getAllServers().forEach(server -> {
      if (server.sendPluginMessage(EVENTS, event.getData()))
        logger.debug("Sent plugin message to %s", server.getServerInfo().getName());
    });
  }
}
