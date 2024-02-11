package com.nookure.staff.api.config.bukkit.partials.messages;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class StaffModePartial {
  @Setting
  @Comment(
      "Message sent to the player when the staff mode is toggled on."
  )
  private String toggledOn = "{prefix} <gray>Staff mode has been toggled <green>on</green>.";

  @Setting
  @Comment(
      "Message sent to the player when the staff mode is toggled off."
  )
  private String toggledOff = "{prefix} <gray>Staff mode has been toggled <red>off</red>.";

  @Setting
  @Comment(
      "Message sent to the player when there are no players online."
  )
  private String noPlayersOnline = "{prefix} <red>There are no players online.";

  @Setting
  @Comment(
      "Message sent to the player when teleporting to another player."
  )
  private String teleportingTo = "{prefix} <gray>Teleporting to <red>{player}</red>.";

  public String toggledOn() {
    return toggledOn;
  }

  public String toggledOff() {
    return toggledOff;
  }

  public String noPlayersOnline() {
    return noPlayersOnline;
  }

  public String teleportingTo() {
    return teleportingTo;
  }
}
