package com.nookure.staff.messaging;

import com.google.inject.Inject;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.event.Event;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.messaging.EventMessenger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NoneEventManager extends EventMessenger {
  @Inject
  private EventManager eventManager;

  @Override
  public void prepare() {
    // Nothing to do here
  }

  @Override
  public void publish(@Nullable PlayerWrapper sender, @NotNull Event event) {
    eventManager.fireEvent(event);
  }

  @Override
  public void publish(@Nullable PlayerWrapper sender, byte @NotNull [] data) {

  }
}
