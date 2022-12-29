package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MAStaff extends JavaPlugin implements MAStaffInstance {
    @Getter
    private static MAStaff plugin;

    @Override
    public void onEnable() {
        plugin = this;
        super.onEnable();
    }


    @Override
    public void drawLogo() {

    }

    @Override
    public void loadConfig() {
        new ConfigLoader().load();
    }

    @Override
    public void registerCommands() {

    }

    @Override
    public void registerListeners() {

    }

    @Override
    public void loadDatabase() {

    }

    @Override
    public void loadModules() {

    }

    @Override
    public void unregisterCommands() {

    }

    @Override
    public void unregisterListeners() {

    }

    @Override
    public void unloadDatabase() {

    }

    @Override
    public void reload() {

    }
}
