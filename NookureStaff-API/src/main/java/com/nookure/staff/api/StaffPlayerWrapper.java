package com.nookure.staff.api;

import com.nookure.staff.api.item.StaffItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface StaffPlayerWrapper extends PlayerWrapper {
  /**
   * toggle the staff mode
   * and save the inventory.
   */
  default void toggleStaffMode() {
    toggleStaffMode(true);
  }

  /**
   * Toggle the staff mode.
   *
   * @param silentJoin if true, the inventory will be saved
   *                   and the player will enter silently.
   */
  void toggleStaffMode(boolean silentJoin);

  /**
   * Check if the player is in staff mode.
   *
   * @return true if the player is in staff mode
   */
  boolean isInStaffMode();

  /**
   * Toggle the vanish mode.
   */
  void toggleVanish();

  /**
   * Check if the player is in vanish mode.
   *
   * @return true if the player is in vanish mode
   * @see #toggleVanish()
   */
  boolean isInVanish();

  /**
   * Reload/load the player's items.
   */
  void setItems();

  /**
   * Get the player's items.
   * @return the player's items
   */
  @NotNull Map<String, StaffItem> getItems();

  /**
   * Save the player's items.
   */
  void saveItems();

  /**
   * Clear the player's items.
   */
  void clearItems();

  /**
   * This will restore the player's items.
   * from the binary user file.
   */
  void restoreItems();
}
