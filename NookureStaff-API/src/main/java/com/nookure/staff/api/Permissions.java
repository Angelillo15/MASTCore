package com.nookure.staff.api;

/**
 * Contains all the permissions used by the plugin.
 *
 * @since 1.0.0
 */
public final class Permissions {
  /**
   * General permission for staff members.
   * This permission is required to use any staff command.
   * With this permission you also will appear as a {@link StaffPlayerWrapper}
   * instead of a {@link PlayerWrapper}.
   */
  public static final String STAFF_PERMISSION = "nookure.staff";
  /**
   * Permission to use the staff mode.
   * This permission is required to use the staff mode.
   */
  public static final String STAFF_MODE_PERMISSION = "nookure.staff.mode";
  /**
   * Permission to use the admin commands.
   * This permission is required to use any admin command.
   */
  public static final String STAFF_ADMIN_PERMISSION = "nookure.staff.admin";
  /**
   * Permission to build in staff mode.
   * This permission is required to build in staff mode.
   */
  public static final String STAFF_MODE_BUILD = "nookure.staff.build";
  /**
   * Permission to enter in vanish.
   */
  public static final String STAFF_VANISH = "nookure.staff.vanish";
  /**
   * Permission to see the players in vanish.
   */
  public static final String STAFF_VANISH_SEE = "nookure.staff.vanish.see";
  /**
   * Permission to freeze a player.
   */
  public static final String STAFF_FREEZE = "nookure.staff.freeze";
  /**
   * Permission to bypass the freeze.
   */
  public static final String STAFF_FREEZE_BYPASS = "nookure.staff.freeze.bypass";
  /**
   * Permission to use the staff chat.
   */
  public static final String STAFF_CHAT = "nookure.staff.staffchat";
  /**
   * Permissions to have administrator notes.
   */
  public static final String STAFF_NOTES = "nookure.staff.notes";
  public static final String STAFF_NOTES_ADMIN = "nookure.staff.notes.admin";

  public static final String STAFF_NOTES_LIST = "nookure.staff.notes.list";

  public static final String STAFF_NOTES_ADD = "nookure.staff.notes.add";

  public static final String STAFF_NOTES_REMOVE = "nookure.staff.notes.remove";

  public static final String STAFF_NOTES_EDIT = "nookure.staff.notes.edit";

  private Permissions() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
}
