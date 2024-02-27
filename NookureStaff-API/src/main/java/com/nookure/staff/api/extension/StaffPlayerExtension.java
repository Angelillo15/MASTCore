package com.nookure.staff.api.extension;

import com.nookure.staff.api.StaffPlayerWrapper;

public abstract class StaffPlayerExtension {
  public StaffPlayerExtension(StaffPlayerWrapper player) {
  }

  /**
   * Called when a player is created
   */
  public void onPlayerCreate() {
  }

  /**
   * Called when a player is destroyed
   */
  public void onPlayerDestroy() {
  }

  public void onStaffModeEnabled() {
  }

  public void onStaffModeDisabled() {
  }
}
