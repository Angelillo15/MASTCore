package com.nookure.staff.api.event.server;

import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record BroadcastMessageExcept(@NotNull String message, @NotNull String permission, @NotNull UUID except) implements Event {
  public BroadcastMessageExcept(@NotNull String message, @NotNull UUID except) {
    this(message, Permissions.STAFF_PERMISSION, except);
  }
}
