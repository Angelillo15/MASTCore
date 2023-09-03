package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.mast.api.chat.api.ChatColor;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.glow.managers.GlowColorManager;

public class GlowLoader {
  public static void loadGlow() {
    GlowColorManager.clearColorList();
    MAStaff.getPlugin().getPLogger().debug("Loading groups colors...");
    ConfigLoader.getGlow()
        .getConfig()
        .getConfigurationSection("Config.groups")
        .getKeys(false)
        .forEach(
            group -> {
              GlowColorManager.addColor(
                  group,
                  ChatColor.of(
                      ConfigLoader.getGlow()
                          .getConfig()
                          .getString("Config.groups." + group + ".color")));

              MAStaff.getPlugin()
                  .getPLogger()
                  .debug(
                      "Added color for: "
                          + group
                          + " with color "
                          + ConfigLoader.getGlow()
                              .getConfig()
                              .getString("Config.groups." + group + ".color"));
            });
  }
}
