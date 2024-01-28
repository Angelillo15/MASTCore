package com.nookure.staff.api.messaging;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.Optional;

public abstract class EventMessenger implements AutoCloseable {
  @Inject
  private Logger logger;
  @Inject
  private NookureStaff plugin;

  /**
   * Prepares the event transport for use.
   */
  public abstract void prepare();

  /**
   * Publishes an event to the event bus.
   *
   * @param sender The sender of the event
   * @param data   The event data
   */
  public abstract void publish(@NotNull PlayerWrapper sender, byte @NotNull[] data);

  @NotNull
  public Optional<Event> decodeEvent(@NotNull ObjectInputStream objetStream) {
    Objects.requireNonNull(objetStream);

    try {
      return Optional.of((Event) objetStream.readObject());
    } catch (IOException | ClassNotFoundException e) {
      logger.severe("Error while decoding event from object stream");
      if (plugin.isDebug()) {
        throw new RuntimeException(e);
      }
    }

    return Optional.empty();
  }

  @Override
  public void close() throws Exception {
  }
}
