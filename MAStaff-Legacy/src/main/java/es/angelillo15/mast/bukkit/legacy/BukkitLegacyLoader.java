package es.angelillo15.mast.bukkit.legacy;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.bukkit.legacy.listener.OnAchievementLegacy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitLegacyLoader {
    public void load(MAStaffInstance<JavaPlugin> instance) {
        loadListeners(instance);
    }

    public void loadListeners(MAStaffInstance<JavaPlugin> instance){
        PluginManager pm = instance.getPluginInstance().getServer().getPluginManager();
        pm.registerEvents(new OnAchievementLegacy(), instance.getPluginInstance());
    }
}