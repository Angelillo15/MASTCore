package es.angelillo15.mast.velocity.listeners;

import com.google.inject.Inject;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.event.Channels;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.PluginMessageManager;
import com.nookure.mast.plugin.IncomingPluginMessageListener;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;

import java.util.Objects;

public class PluginMessageListener extends IncomingPluginMessageListener {
  @Inject
  @SuppressWarnings("rawtypes")
  public PluginMessageListener(PluginMessageManager pluginMessageManager, MAStaff plugin, EventManager eventManager) {
    super(pluginMessageManager, plugin, eventManager);
  }

  @Subscribe
  public void onReceive(PluginMessageEvent event) {
    if (!Objects.equals(event.getIdentifier().getId(), Channels.EVENTS)) return;

    handleEvent(event.getData());
  }
}
