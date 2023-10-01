package com.nookure.mast.api.event.staff.mode;

import com.nookure.mast.api.event.Event;
import es.angelillo15.mast.api.IStaffPlayer;

public class StaffModeDisabledEvent extends Event {
  private final IStaffPlayer staffPlayer;

  public StaffModeDisabledEvent(IStaffPlayer staffPlayer) {
    this.staffPlayer = staffPlayer;
  }

  public IStaffPlayer getStaffPlayer() {
    return staffPlayer;
  }
}
