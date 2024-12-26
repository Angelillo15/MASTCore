package com.nookure.staff.api.event.sync;

import com.nookure.staff.api.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Redis2ServerQuestion extends Event {
  @NotNull UUID packetUUID();

  byte @NotNull [] serialize();

  @NotNull
  Redis2ServerResponse generateResponse();
}
