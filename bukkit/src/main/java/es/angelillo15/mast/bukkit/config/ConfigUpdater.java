package es.angelillo15.mast.bukkit.config;

import es.angelillo15.mast.config.ConfigManager;

public class ConfigUpdater {
    public void update() {
        ConfigManager messages = ConfigLoader.getMessages();
        if(!messages.getConfig().contains("Vanish.enable")) {
            messages.getConfig().set("Vanish.enable", "{prefix} &7You have enabled vanish.");
            messages.getConfig().set("Vanish.disable", "{prefix} &7You have disabled vanish.");
            messages.registerConfig();
        }
    }
}
