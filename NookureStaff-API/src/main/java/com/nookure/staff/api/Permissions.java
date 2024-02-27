package com.nookure.staff.api;

/**
 * Contains all the permissions used by the plugin.
 *
 * @since 1.0.0
 */
public final class Permissions {
  private Permissions() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }

  /**
   * General permission for staff members.
   * This permission is required to use any staff command.
   * With this permission you also will appear as a {@link StaffPlayerWrapper}
   * instead of a {@link PlayerWrapper}.
   */
  public static final String STAFF_PERMISSION = "nookure.staff";

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
}
