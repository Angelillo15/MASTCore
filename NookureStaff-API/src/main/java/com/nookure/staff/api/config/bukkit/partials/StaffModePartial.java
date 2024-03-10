package com.nookure.staff.api.config.bukkit.partials;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

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
  @Setting
  @Comment("""
      Enable or disable night vision when the player enters the staff mode.
      """)
  private boolean nightVision = true;

  @Setting
  @Comment("""
      Enable or disable custom potion effects when the player enters the staff mode.
      """)
  private boolean customPotionEffects = false;

  @Setting
  @Comment("""
      Potion effects to add when the player enters the staff mode.
      Format: "effect:level:duration"
       """)
  private List<String> potionEffects = List.of(
      "speed:1:999999",
      "jump:1:999999"
  );

  public boolean silentChestOpen() {
    return silentChestOpen;
  }

  public boolean teleportToPreviousLocation() {
    return teleportToPreviousLocation;
  }

  public boolean nightVision() {
    return nightVision;
  }

  public boolean customPotionEffects() {
    return customPotionEffects;
  }

  public List<String> potionEffects() {
    return potionEffects;
  }
}
