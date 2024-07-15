package com.nookure.staff.api.config.bukkit.partials.messages;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class VanishPartial {
  @Setting
  @Comment("Message sent to the player when the vanish is toggled on.")
  private final String vanishEnabled = "{prefix} <gray>Vanish has been toggled <green>on</green>.";

  @Setting
  @Comment("""
      Message to broadcast to online players when a staff member enable vanish.
      Change it to "" to disable the broadcast.
      """)
  private final String vanishEnabledBroadcast = "<yellow>{player} has left the game.";

  @Setting
  @Comment("Message sent to the player when the vanish is toggled off.")
  private final String vanishDisabled = "{prefix} <gray>Vanish has been toggled <red>off</red>.";

  @Setting
  @Comment("""
      Message to broadcast to online players when a staff member disable vanish.
      Change it to "" to disable the broadcast.
      """)
  private final String vanishDisabledBroadcast = "<yellow>{player} has joined the game.";

  @Setting
  @Comment("Message sent to the player when he/she joins the server and is still in vanish mode.")
  private final String youAreStillInVanish = "<green>You are still in vanish mode.";

  public String vanishEnabled() {
    return vanishEnabled;
  }

  public String vanishDisabled() {
    return vanishDisabled;
  }

  public String vanishEnabledBroadcast() {
    return vanishEnabledBroadcast;
  }

  public String vanishDisabledBroadcast() {
    return vanishDisabledBroadcast;
  }

  public String youAreStillInVanish() {
    return youAreStillInVanish;
  }
}
