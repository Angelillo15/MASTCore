package com.nookure.mast.api.event.staff;

import com.nookure.mast.api.event.Event;

public record StaffServerSwitchEvent(String username, String fromServer, String toServer) implements Event {
}
