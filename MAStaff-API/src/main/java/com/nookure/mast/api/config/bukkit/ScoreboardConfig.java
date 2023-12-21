package com.nookure.mast.api.config.bukkit;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class ScoreboardConfig {
  private boolean enabled = true;

  public boolean enabled() {
    return enabled;
  }

  @ConfigSerializable
  public static final class Scoreboard {
    private String title = "<b><dark_aqua>Staff Mode</dark_aqua></b>";

    public String title() {
      return title;
    }

    private List<String> lines = List.of(
        "<b><yellow>|</yellow></b> <white>Rank:</white> <b><red>%vault_prefix%</red></b>",
        "<b><yellow>|</yellow></b> <white>Vanished:</white> <green>True</green>",
        "",
        "<b><red>|</red></b> <white>Staffs online:</white> <green>%mastaff_staffcount%</green>",
        "<b><red>|</red></b> <white>Online players:</white> <green>%server_online%</green>",
        "<b><red>|</red></b> <white>Frozen players:</white> <green>%mastaff_freeze_count%</green>"
    );

    public List<String> lines() {
      return lines;
    }
  }
}
