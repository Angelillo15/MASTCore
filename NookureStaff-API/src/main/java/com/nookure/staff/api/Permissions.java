package com.nookure.staff.api;

/**
 * Contains all the permissions used by the plugin.
 *
 * @since 1.0.0
 */
public class Permissions {
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
}
