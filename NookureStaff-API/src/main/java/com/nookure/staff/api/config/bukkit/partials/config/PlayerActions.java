package com.nookure.staff.api.config.bukkit.partials.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class PlayerActions {
  @Comment("""
      If true, the player will be able to inspect a player by
      shift and right-clicking on them.
      """)
  private final boolean shiftAndRightClickToInspect = true;

  public boolean shiftAndRightClickToInspect() {
    return shiftAndRightClickToInspect;
  }
}
