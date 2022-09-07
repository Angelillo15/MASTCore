package es.angelillo15.mast.bukkit.config;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.config.ConfigManager;


import java.io.File;
import java.io.IOException;


public class ConfigLoader {
    private MASTBukkitManager plugin;
    private static ConfigManager config;
    private static ConfigManager messages;
    private static ConfigManager staffItems;
    private String language;

    public ConfigLoader(MASTBukkitManager plugin) {
        this.plugin = plugin;
    }

    public void load() {
        loadConfig();
        loadLanguages();
        loadMessage();
    }

    public void loadConfig() {
        config = new ConfigManager(plugin.getDataFolder().toPath(), "config.yml", "config.yml");
        config.registerConfig();
        staffItems = new ConfigManager(plugin.getDataFolder().toPath(), "config.yml", "config.yml");
        staffItems.registerConfig();


    }

    public void loadLanguages() {
        File file = new File(plugin.getDataFolder().toPath().toString() + File.separator + "lang");
        if (!file.exists()) {
            file.mkdir();
        }

        ConfigManager es = new ConfigManager(plugin.getDataFolder().toPath(), "lang/spanish.yml", "/lang/spanish.yml");
        ConfigManager en = new ConfigManager(plugin.getDataFolder().toPath(), "lang/english.yml", "/lang/english.yml");
        es.registerConfig();
        en.registerConfig();

    }

    public void loadMessage() {
        language = config.getConfig().getString("Config.language");
        String lang = "/lang/" + language;

        messages = new ConfigManager(plugin.getDataFolder().toPath(), lang, lang);
        messages.registerConfig();
        try {
            messages.getConfig().load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static ConfigManager getConfig() {
        return config;
    }

    public static ConfigManager getMessages() {
        return messages;
    }

    public static ConfigManager getStaffItems() {
        return staffItems;
    }
}
