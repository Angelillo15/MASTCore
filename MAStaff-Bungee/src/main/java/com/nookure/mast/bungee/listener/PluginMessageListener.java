package com.nookure.mast.bungee.listener;

import com.google.inject.Inject;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.event.Channels;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.PluginMessageManager;
import com.nookure.mast.plugin.IncomingPluginMessageListener;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Objects;

public class PluginMessageListener extends IncomingPluginMessageListener implements Listener {
  @Inject
  @SuppressWarnings("rawtypes")
  public PluginMessageListener(PluginMessageManager pluginMessageManager, MAStaff plugin, EventManager eventManager) {
    super(pluginMessageManager, plugin, eventManager);
  }

  @EventHandler
  public void onPluginMessage(PluginMessageEvent event) {
    if (!Objects.equals(event.getTag(), Channels.EVENTS)) {
      return;
    }

    handleEvent(event.getData());
  }
}
