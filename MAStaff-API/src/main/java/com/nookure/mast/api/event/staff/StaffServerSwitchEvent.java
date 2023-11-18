package com.nookure.mast.api.event.staff;

import com.nookure.mast.api.event.Event;

public class StaffServerSwitchEvent extends Event {
  private final String username;
  private final String fromServer;
  private final String toServer;

  public StaffServerSwitchEvent(String username, String fromServer, String toServer) {
    this.username = username;
    this.fromServer = fromServer;
    this.toServer = toServer;
  }

  public String getUsername() {
    return username;
  }

  public String getFromServer() {
    return fromServer;
  }

  public String getToServer() {
    return toServer;
  }
}
