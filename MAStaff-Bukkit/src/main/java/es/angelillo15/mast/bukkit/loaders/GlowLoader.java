package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.glow.data.glow.Glow;
import es.angelillo15.mast.api.managers.GlowManager;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import org.bukkit.ChatColor;

public class GlowLoader {
    public static void loadGlow(){
        generateGlow(
                ChatColor.AQUA,
                ChatColor.BLUE,
                ChatColor.DARK_AQUA,
                ChatColor.DARK_BLUE,
                ChatColor.DARK_GRAY,
                ChatColor.DARK_GREEN,
                ChatColor.DARK_PURPLE,
                ChatColor.DARK_RED,
                ChatColor.GOLD,
                ChatColor.GRAY,
                ChatColor.GREEN,
                ChatColor.LIGHT_PURPLE,
                ChatColor.RED,
                ChatColor.WHITE,
                ChatColor.YELLOW
        );

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

    private static Glow generateGlow(ChatColor color){
        return Glow.builder()
                .color(color)
                .name(color.toString())
                .build();
    }

    private static void generateGlow(ChatColor... color){
        for(ChatColor c : color){
            GlowManager.addGlow(c, generateGlow(c));
        }
    }

}
