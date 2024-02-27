package com.nookure.staff.api;

import com.nookure.staff.api.extension.StaffPlayerExtension;
import com.nookure.staff.api.item.StaffItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public interface StaffPlayerWrapper extends PlayerWrapper {
  /**
   * toggle the staff mode
   * and save the inventory.
   */
  default void toggleStaffMode() {
    toggleStaffMode(false);
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
   * Save the player's items.
   */
  void saveInventory();

  /**
   * Enable the vanish mode.
   *
   * @param silent if true, the message will not be sent
   */
  void enableVanish(boolean silent);

  /**
   * Disable the vanish mode.
   *
   * @param silent if true, the message will not be sent
   */
  void disableVanish(boolean silent);

  /**
   * Enable the vanish mode.
   * The message will be sent.
   */
  default void enableVanish() {
    enableVanish(false);
  }

  /**
   * Disable the vanish mode.
   * The message will be sent.
   */
  default void disableVanish() {
    disableVanish(false);
  }

  /**
   * Restore the player's items.
   */
  void restoreInventory();

  /**
   * Clear the player's items.
   */
  void clearInventory();

  /**
   * Enable the player's perks.
   */
  void disablePlayerPerks();

  /**
   * Disable the player's perks.
   */
  void enablePlayerPerks();

  /**
   * Return the player's staff mode extension.
   *
   * @param extension the extension class that you want to get
   * @return the extension
   */
  <T extends StaffPlayerExtension> Optional<T> getExtension(Class<T> extension);
  /**
   * Get the player's items.
   *
   * @return the player's items
   */
  @NotNull Map<Integer, StaffItem> getItems();
}
