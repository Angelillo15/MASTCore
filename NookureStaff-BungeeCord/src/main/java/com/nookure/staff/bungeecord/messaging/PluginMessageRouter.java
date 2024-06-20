package com.nookure.staff.bungeecord.messaging;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.messaging.Channels;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.bungeecord.NookureStaff;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Objects;

public class PluginMessageRouter implements Listener {
  @Inject
  private NookureStaff plugin;
  @Inject
  private Logger logger;
  @Inject
  private EventMessenger messenger;

  @EventHandler
  public void onPluginMessageFromPlayer(PluginMessageEvent event) {
    if (!Objects.equals(event.getTag(), Channels.EVENTS)) {
      return;
    }

    logger.debug("Routing plugin message to all servers.");

    messenger.decodeEvent(event.getData());

    plugin.getProxy().getServers().values().forEach(server -> {
      server.sendData(Channels.EVENTS, event.getData());
      logger.debug("Sent plugin message to %s", server.getName());
    });
  }
}
