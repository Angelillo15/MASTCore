package com.nookure.mast.api.event.message;

import com.nookure.mast.api.event.Event;

public record BackendStaffChatEvent(String message, String player, String server) implements Event {
}
