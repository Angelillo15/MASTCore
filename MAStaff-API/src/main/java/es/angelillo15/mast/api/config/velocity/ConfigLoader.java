package es.angelillo15.mast.api.config.velocity;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.managers.ConfigMerge;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigLoader {
    @Getter
    private static ConfigManager config;
    @Getter
    private static ConfigManager messages;
    @Getter
    private static ConfigManager es;
    @Getter
    private static ConfigManager en;
    private String language;
    private Path path;
    private MAStaffInstance<?> plugin;

    public ConfigLoader(Path path, MAStaffInstance<?> plugin) {
        ConfigMerge.loggerSetup();
        this.path = path;
        this.plugin = plugin;
    }

    public void load() {
        ILogger logger = plugin.getPLogger();
        loadConfig();
        logger.debug("Loaded config");
        logger.debug("Loading languages...");
        loadLanguages();
        logger.debug("Loading messages...");
        loadMessage();
        logger.debug("Configs loaded!");
    }

    public void loadConfig() {
        ConfigMerge.merge(new File(path + File.separator + "config.yml"),
                plugin.getPluginResource("Velocity/config.yml")
        );

        config = new ConfigManager(path, "Velocity/config.yml", "config.yml");
        config.registerConfig();

        plugin.setDebug(config.getConfig().getBoolean("Config.debug"));
    }


    public void loadLanguages() {
        File file = new File(path + File.separator + "lang");
        if (!file.exists()) {
            file.mkdir();
        }

        ConfigMerge.merge(new File(path + File.separator + "lang/spanish.yml"),
                plugin.getPluginResource("BungeeCord/lang/spanish.yml")
        );

        ConfigMerge.merge(new File(path + File.separator + "lang/english.yml"),
                plugin.getPluginResource("BungeeCord/lang/english.yml")
        );

        es = new ConfigManager(path, "BungeeCord/lang/spanish.yml", "/lang/spanish.yml");
        en = new ConfigManager(path, "BungeeCord/lang/english.yml", "/lang/english.yml");
        en.registerConfig();
        es.registerConfig();
    }

    public void loadMessage() {
        language = config.getConfig().getString("Config.language");
        String lang = "lang/" + language;

        messages = new ConfigManager(path, "/BungeeCord/" + lang, lang);
        messages.registerConfig();
        try {
            messages.getConfig().load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
