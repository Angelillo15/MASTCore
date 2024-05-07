package com.nookure.staff.waterfall.module;

import com.nookure.staff.api.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.plugin.Plugin;

public class WaterfallLogger implements Logger {
  private final Plugin plugin;

  public WaterfallLogger(Plugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void info(Component component) {
    plugin.getLogger().info(LegacyComponentSerializer.legacy('§').serialize(component));
  }

  @Override
  public void warning(Component component) {
    plugin.getLogger().warning(LegacyComponentSerializer.legacy('§').serialize(component));
  }

  @Override
  public void severe(Component component) {
    plugin.getLogger().severe(LegacyComponentSerializer.legacy('§').serialize(component));
  }

  @Override
  public void debug(Component component) {
    if (System.getProperty("nookure.debug") != null)
      plugin.getLogger().info(LegacyComponentSerializer.legacy('§').serialize(component));
  }
}
