package es.angelillo15.mast.bukkit.config;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.config.ConfigManager;
import org.apache.commons.lang.text.StrTokenizer;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ConfigLoader {
    private MASTBukkitManager plugin;
    private ConfigManager config;
    private ConfigManager messages;
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
    }

    public void loadLanguages() {
        ConfigManager en = new ConfigManager(plugin.getDataFolder().toPath(), "lang/english.yml", "lang/english.yml");
        ConfigManager es = new ConfigManager(plugin.getDataFolder().toPath(), "lang/spanish.yml", "lang/spanish.yml");
        en.registerConfig();
        es.registerConfig();

    }

    public void loadMessage() {
        language = config.getConfig().getString("Config.language");
        String lang = "lang/" + language;

        messages = new ConfigManager(plugin.getDataFolder().toPath(), lang, lang);
        messages.registerConfig();
        try {
            messages.getConfig().load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
