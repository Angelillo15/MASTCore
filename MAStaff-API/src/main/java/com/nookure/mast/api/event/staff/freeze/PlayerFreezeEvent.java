package com.nookure.mast.api.event.staff.freeze;

import com.nookure.mast.api.event.Event;

public record PlayerFreezeEvent(String staffName, String playerName) implements Event {
}
