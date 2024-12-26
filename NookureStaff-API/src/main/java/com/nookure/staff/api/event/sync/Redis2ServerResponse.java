package com.nookure.staff.api.event.sync;

import com.nookure.staff.api.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Redis2ServerResponse extends Event {
  @NotNull UUID packetUUID();

  byte @NotNull [] serialize();

  Redis2ServerResponse generateResponse(UUID packetUUID);
}
