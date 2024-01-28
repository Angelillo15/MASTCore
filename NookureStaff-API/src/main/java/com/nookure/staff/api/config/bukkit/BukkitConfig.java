package com.nookure.staff.api.config.bukkit;

import com.nookure.staff.api.config.bukkit.partials.ModulesPartials;
import com.nookure.staff.api.config.partials.DatabaseConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class BukkitConfig {
  @Setting
  @Comment("""
      Enable or disable debug mode.
      This will print out more information to the console.
      It's recommended to find bugs.
      """)
  private boolean debug = false;

  public boolean isDebug() {
    return debug;
  }

  @Setting
  public final DatabaseConfig database = new DatabaseConfig();
  @Setting
  @Comment("""
      Here you can enable or disable any part of the plugin.
      If you disable a module, the commands and the features
      of that module will be disabled.
      """)
  public final ModulesPartials modules = new ModulesPartials();
}
