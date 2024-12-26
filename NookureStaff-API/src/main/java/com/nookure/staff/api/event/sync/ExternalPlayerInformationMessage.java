package com.nookure.staff.api.event.sync;

import com.nookure.staff.api.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ExternalPlayerInformationMessage(
    @NotNull UUID uuid,
    @NotNull String username,
    boolean isOp,
    boolean isStaff
) implements Event {
}
