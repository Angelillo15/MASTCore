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

  public boolean silentChestOpen() {
    return silentChestOpen;
  }
}
