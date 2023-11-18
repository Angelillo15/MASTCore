package com.nookure.mast.api.event.staff;

import com.nookure.mast.api.event.Event;

public class StaffJoinEvent extends Event {
  private final String staffName;

  public StaffJoinEvent(String staffName) {
    this.staffName = staffName;
  }

  public String getStaffName() {
    return staffName;
  }
}
