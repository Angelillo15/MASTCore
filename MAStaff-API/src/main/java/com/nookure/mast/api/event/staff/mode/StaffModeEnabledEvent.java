package com.nookure.mast.api.event.staff.mode;

import com.nookure.mast.api.event.Event;
import es.angelillo15.mast.api.IStaffPlayer;

public record StaffModeEnabledEvent(IStaffPlayer staffPlayer) implements Event {
}
