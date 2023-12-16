package com.nookure.mast.api.event;

import com.google.inject.Inject;
import com.nookure.mast.api.MAStaff;
import es.angelillo15.mast.api.ILogger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.Optional;

public abstract class PluginMessageManager<P> {
  @Inject
  private ILogger logger;
  @Inject
  private MAStaff plugin;
  abstract public void sendEvent(@NotNull Event event, @NotNull P player);

  @NotNull
  public Optional<Event> decodeEvent(@NotNull ObjectInputStream objetStream) {
    Objects.requireNonNull(objetStream);

    try {
      return Optional.of((Event) objetStream.readObject());
    } catch (IOException | ClassNotFoundException e) {
      logger.error("Error while decoding event from object stream");
      if (plugin.isDebug()) {
        throw new RuntimeException(e);
      }
    }

    return Optional.empty();
  }
}
