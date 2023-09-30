package com.nookure.mast.api.staff;

import es.angelillo15.mast.api.IStaffPlayer;

public interface StaffFeature {
  /**
   * Called when the staff player enters in staff mode
   *
   * @param player the staff player who enters in staff mode
   */
  default void onStaffEnable(IStaffPlayer player) {
    // Needs to be implemented
  }

  /**
   * Called when the staff player leaves the staff mode
   *
   * @param player the staff player who leaves the staff mode
   */
  default void onStaffDisable(IStaffPlayer player) {
    // Needs to be implemented
  }
}
