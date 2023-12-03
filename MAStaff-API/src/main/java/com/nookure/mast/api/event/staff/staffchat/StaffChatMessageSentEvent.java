package com.nookure.mast.api.event.staff.staffchat;

import com.nookure.mast.api.event.Event;

public class StaffChatMessageSentEvent extends Event {
  private final String message;
  private final String username;
  private final String server;

  public StaffChatMessageSentEvent(String message, String username, String server) {
    this.message = message;
    this.username = username;
    this.server = server;
  }

  public String getMessage() {
    return message;
  }

  public String getUsername() {
    return username;
  }

  public String getServer() {
    return server;
  }
}
