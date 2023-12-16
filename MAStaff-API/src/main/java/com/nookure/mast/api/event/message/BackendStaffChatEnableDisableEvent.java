package com.nookure.mast.api.event.message;

import com.nookure.mast.api.event.Event;

public record BackendStaffChatEnableDisableEvent(String username) implements Event {
}
