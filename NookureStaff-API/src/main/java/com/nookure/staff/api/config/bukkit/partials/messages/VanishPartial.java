package com.nookure.staff.api.config.bukkit.partials.messages;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class VanishPartial {
  @Setting
  @Comment("Message sent to the player when the vanish is toggled on.")
  private String vanishEnabled = "{prefix} <gray>Vanish has been toggled <green>on</green>.";

  @Setting
  @Comment("Message sent to the player when the vanish is toggled off.")
  private String vanishDisabled = "{prefix} <gray>Vanish has been toggled <red>off</red>.";

  public String vanishEnabled() {
    return vanishEnabled;
  }

  public String vanishDisabled() {
    return vanishDisabled;
  }
}
