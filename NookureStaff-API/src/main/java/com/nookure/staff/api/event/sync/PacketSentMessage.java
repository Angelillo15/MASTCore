package com.nookure.staff.api.event.sync;

import com.nookure.staff.api.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record PacketSentMessage(@NotNull UUID server, byte @NotNull [] key) implements Event {
}
