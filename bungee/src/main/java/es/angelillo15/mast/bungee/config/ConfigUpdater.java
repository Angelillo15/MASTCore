package es.angelillo15.mast.bungee.config;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import es.angelillo15.mast.config.ConfigManager;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;

public class ConfigUpdater {
    public void update() {
        YamlFile messages = ConfigLoader.getMessages().getConfig();
        if(!(messages.contains("StaffChat.enabled"))) {
            ConfigLoader.getMessages().getConfig().set("StaffChat.enabled", "{prefix} &7You have &aenabled &7Staff Chat.");
            ConfigLoader.getMessages().getConfig().set("StaffChat.disabled", "{prefix} &7You have &adisabled &7Staff Chat.");
            try {
                ConfigLoader.getMessages().getConfig().save();
                ConfigLoader.getMessages().registerConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MASTBungeeManager.getInstance().getProxy().getLogger().info("Updated messages.yml");
        }
    }
}