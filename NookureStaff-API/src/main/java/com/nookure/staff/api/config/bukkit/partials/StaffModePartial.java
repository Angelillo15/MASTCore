package com.nookure.staff.api.config.bukkit.partials;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class StaffModePartial {
  @Comment(
      """
          If true when a chest is opened by a staff member, the chest will not make any sound
          or animation.
          """
  )
  @Setting
  private boolean silentChestOpen = true;

  @Comment(
      """
          If true when a staff disables staff mode, they will be teleported to their previous location.
          This is useful for preventing staff members abusing the staff mode to teleport to a player or
          fly to a location.
          """
  )
  @Setting
  private boolean teleportToPreviousLocation = true;

  public boolean silentChestOpen() {
    return silentChestOpen;
  }

  public boolean teleportToPreviousLocation() {
    return teleportToPreviousLocation;
  }
}
