package com.nookure.mast.api.event.staff.staffchat;

import com.nookure.mast.api.event.Event;

public record StaffChatMessageSentEvent(String message, String username, String server) implements Event {
}
