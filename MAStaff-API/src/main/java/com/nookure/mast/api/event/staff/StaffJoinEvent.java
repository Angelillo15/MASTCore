package com.nookure.mast.api.event.staff;

import com.nookure.mast.api.event.Event;

public record StaffJoinEvent(String staffName) implements Event {
}
