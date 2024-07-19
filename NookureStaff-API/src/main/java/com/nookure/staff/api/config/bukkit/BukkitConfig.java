package com.nookure.staff.api.config.bukkit;

import com.nookure.staff.api.config.bukkit.partials.config.FreezePartial;
import com.nookure.staff.api.config.bukkit.partials.config.ModulesPartials;
import com.nookure.staff.api.config.bukkit.partials.config.PlayerActions;
import com.nookure.staff.api.config.bukkit.partials.config.StaffModePartial;
import com.nookure.staff.api.config.partials.DatabaseConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class BukkitConfig {
  @Setting
  public final DatabaseConfig database = new DatabaseConfig();

  @Setting
  @Comment("""
      Here you can enable or disable any part of the plugin.
      If you disable a module, the commands and the features
      of that module will be disabled.
      """)
  public final ModulesPartials modules = new ModulesPartials();

  @Setting
  @Comment("""
      Here you can configure some settings for the staffmode
      """)
  public final StaffModePartial staffMode = new StaffModePartial();

  @Setting
  @Comment("""
      Here you can configure some settings for the freeze module
      """)
  public final FreezePartial freeze = new FreezePartial();

  @Setting
  @Comment("""
      Here you can configure some settings for the player actions
      """)
  public final PlayerActions playerActions = new PlayerActions();

  @Setting
  @Comment("""
      Enable or disable debug mode.
      This will print out more information to the console.
      It's recommended to find bugs.
      """)
  private boolean debug = false;

  @Setting
  @Comment("""
      The name of the server.
      This will be used in the messages or identifiers.
      """)
  private String serverName = "Server 1";

  public boolean isDebug() {
    return debug;
  }

  public String getServerName() {
    return serverName;
  }
}
