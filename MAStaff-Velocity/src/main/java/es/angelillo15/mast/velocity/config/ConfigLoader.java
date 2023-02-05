package es.angelillo15.mast.velocity.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.velocity.MAStaffLoader;
import lombok.Getter;

public class ConfigLoader {
    @Getter
    private static ConfigManager config;

    public static void load(){
        loadConfig();
    }

    public static void loadConfig(){
        config = new ConfigManager(MAStaffLoader.getPlugin().getDataFolder(), "velocity/config.yml", "config.yml");
        config.registerConfig();
    }
}
