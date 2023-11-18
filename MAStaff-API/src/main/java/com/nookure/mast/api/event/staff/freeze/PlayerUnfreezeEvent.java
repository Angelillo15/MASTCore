package com.nookure.mast.api.event.staff.freeze;

import com.nookure.mast.api.event.Event;

public class PlayerUnfreezeEvent extends Event {
  private final String staffName;
  private final String playerName;

  public PlayerUnfreezeEvent(String staffName, String playerName) {
    this.staffName = staffName;
    this.playerName = playerName;
  }

  public String getStaffName() {
    return staffName;
  }

  public String getPlayerName() {
    return playerName;
  }
}
