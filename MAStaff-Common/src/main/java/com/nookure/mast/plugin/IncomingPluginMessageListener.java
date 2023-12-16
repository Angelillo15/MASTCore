package com.nookure.mast.plugin;

import com.google.inject.Inject;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.PluginMessageManager;
import es.angelillo15.mast.api.ILogger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class IncomingPluginMessageListener {
  private final PluginMessageManager<?> pluginMessageManager;
  private final ILogger logger;
  private final MAStaff plugin;
  private final EventManager eventManager;

  @Inject
  @SuppressWarnings("rawtypes")
  public IncomingPluginMessageListener(
      PluginMessageManager pluginMessageManager,
      MAStaff plugin,
      EventManager eventManager
  ) {
    this.pluginMessageManager = pluginMessageManager;
    this.logger = plugin.getPLogger();
    this.plugin = plugin;
    this.eventManager = eventManager;
  }

  public void handleEvent(byte[] data) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

      pluginMessageManager.decodeEvent(objectInputStream).ifPresent(event -> {
        logger.debug("Received event " + event.getClass().getSimpleName() + " from plugin message");
        eventManager.fireEvent(event);
      });

    } catch (IOException e) {
      logger.error("Error while decoding event from object stream");
      if (plugin.isDebug()) throw new RuntimeException(e);
    }
  }
}
