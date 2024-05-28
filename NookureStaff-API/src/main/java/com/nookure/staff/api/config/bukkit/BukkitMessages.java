package com.nookure.staff.api.config.bukkit;

import com.nookure.staff.api.config.bukkit.partials.messages.*;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class BukkitMessages {
  public final StaffModePartial staffMode = new StaffModePartial();
  public final VanishPartial vanish = new VanishPartial();
  public final FreezeMessagePartial freeze = new FreezeMessagePartial();
  public final StaffChatPartial staffChat = new StaffChatPartial();
  public final PlaceholderPartial placeholder = new PlaceholderPartial();
  @Setting
  @Comment(
      """
          The prefix for all staff messages.
          If you want to use the prefix in a message, use {prefix}.
              """
  )
  private String prefix = "<b><red>Staff</red> <gray>»</gray></b>";
  @Setting
  @Comment(
      """
          Reload message
          """
  )
  private String reload = "{prefix} <gray>Configuration reloaded, mayor changes may not take effect until the server is restarted.";
  @Setting
  @Comment(
      """
          Player not found message
          """
  )
  private String playerNotFound = "{prefix} <red>Could not find the player {player}.";

  public String prefix() {
    return prefix;
  }

  public String reload() {
    return reload;
  }

  public String playerNotFound() {
    return playerNotFound;
  }
}
