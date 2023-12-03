package com.nookure.mast.api.event.staff;

import com.nookure.mast.api.event.Event;

public class StaffLeaveEvent extends Event {
  private final String staffName;

  public StaffLeaveEvent(String staffName) {
    this.staffName = staffName;
  }

  public String getStaffName() {
    return staffName;
  }
}
