package es.angelillo15.mast.bungee.config;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import es.angelillo15.mast.config.ConfigManager;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private MASTBungeeManager plugin;
    private static ConfigManager config;
    private static ConfigManager messages;
    private String language;
    @Getter
    private static ConfigManager es;
    @Getter
    private static ConfigManager en;

    public ConfigLoader(MASTBungeeManager plugin) {
        this.plugin = plugin;
    }

    public void load() {
        loadConfig();
        loadLanguages();
        loadMessage();
    }

    public void loadConfig() {
        config = new ConfigManager(plugin.getDataFolder().toPath(), "BungeeCord/config.yml", "config.yml");
        config.registerConfig();
    }

    public void loadLanguages() {
        File file = new File(plugin.getDataFolder().toPath().toString() + File.separator + "lang");
        if (!file.exists()) {
            file.mkdir();
        }

        es = new ConfigManager(plugin.getDataFolder().toPath(), "BungeeCord/lang/spanish.yml", "/lang/spanish.yml");
        en = new ConfigManager(plugin.getDataFolder().toPath(), "BungeeCord/lang/english.yml", "/lang/english.yml");
        en.registerConfig();
        es.registerConfig();

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
}
