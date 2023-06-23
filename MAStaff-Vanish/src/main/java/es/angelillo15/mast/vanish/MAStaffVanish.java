package es.angelillo15.mast.vanish;

import es.angelillo15.mast.api.addons.MAStaffAddon;
import org.bukkit.plugin.java.JavaPlugin;

public class MAStaffVanish extends MAStaffAddon<JavaPlugin> {
    @Override
    public void onEnable() {
        getLogger().info("Enabling MAStaff Vanish module...");
        loadConfig();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void reload() {

    }
}
