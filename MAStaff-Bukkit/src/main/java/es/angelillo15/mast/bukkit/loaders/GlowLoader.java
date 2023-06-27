package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.mast.api.managers.GlowManager;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import org.bukkit.ChatColor;

public class GlowLoader {
    public static void loadGlow(){
        GlowManager.clearColorList();
        MAStaff.getPlugin().getPLogger().debug("Loading groups colors...");
        ConfigLoader.getGlow().getConfig().getConfigurationSection("Config.groups").getKeys(false).forEach(group -> {
            GlowManager.addColor(group, ChatColor.valueOf(ConfigLoader.getGlow().getConfig()
                    .getString("Config.groups." + group + ".color"))
            );

            MAStaff.getPlugin().getPLogger().debug("Added color for: " + group + " with color "
                    + ConfigLoader.getGlow()
                    .getConfig().getString("Config.groups." + group + ".color")
            );
        });
    }
}
