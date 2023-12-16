package es.angelillo15.mast.bukkit.listener;

import com.google.inject.Inject;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.event.Channels;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.PluginMessageManager;
import com.nookure.mast.plugin.IncomingPluginMessageListener;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PluginMessageListener extends IncomingPluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
  @Inject
  @SuppressWarnings("rawtypes")
  public PluginMessageListener(PluginMessageManager pluginMessageManager, MAStaff plugin, EventManager eventManager) {
    super(pluginMessageManager, plugin, eventManager);
  }

  @Override
  public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
    if (!channel.equals(Channels.EVENTS)) return;

    handleEvent(message);
  }
}
