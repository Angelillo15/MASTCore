package es.angelillo15.mast.bukkit.config;

import es.angelillo15.mast.config.ConfigManager;

import java.io.IOException;

public class ConfigUpdater {
    public void update() {
        ConfigManager messages = ConfigLoader.getMessages();
        if(!messages.getConfig().contains("Vanish.enable")) {
            messages.getConfig().set("Vanish.enable", "{prefix} &7You have &aenabled &7vanish.");
            messages.getConfig().set("Vanish.disable", "{prefix} &7You have &adisabled &7vanish.");
            try {
                messages.getConfig().save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            messages.registerConfig();
        }
    }
}
