package es.angelillo15.mast.api.utils;

import es.angelillo15.mast.api.TextUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitUtils {
    public static void setAudienceBukkit(JavaPlugin plugin) {
        TextUtils.setAudienceProvider(BukkitAudiences.create(plugin));
    }
}
