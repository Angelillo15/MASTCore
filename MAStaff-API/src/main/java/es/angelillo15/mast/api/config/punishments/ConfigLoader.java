package es.angelillo15.mast.api.config.punishments;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.managers.ConfigMerge;
import java.io.File;
import java.io.IOException;
import lombok.Getter;

public class ConfigLoader {
    private static MAStaffAddon<?> plugin;
    @Getter
    private static ConfigManager messages;
    @Getter
    private static ConfigManager es;
    @Getter
    private static ConfigManager en;
    @Getter
    private static ConfigManager config;
    @Getter
    private static ConfigManager banTemplate;
    @Getter
    private static ConfigManager warnsTemplate;

    public static void load(MAStaffAddon<?> plugin) {
        ConfigLoader.plugin = plugin;
        loadConfig();
        loadLanguages();
        loadMessage();

        banTemplate = loadFile("Punishments/templates/ban.yml","templates/ban.yml", plugin);
        warnsTemplate = loadFile("Punishments/templates/warn.yml","templates/warn.yml", plugin);
    }

    public static void loadConfig() {
        ConfigMerge.merge(new File(plugin.getAddonFolder().toPath() + File.separator + "config.yml"),
                plugin.getResourceAsStream("Punishments/config.yml")
        );

        config = new ConfigManager(plugin.getAddonFolder().toPath(), "Punishments/config.yml", "config.yml");
        config.registerConfig();
    }

    public static void loadLanguages() {
        File file = new File(plugin.getAddonFolder().toPath() + File.separator + "lang");
        if (!file.exists()) {
            file.mkdirs();
        }

        ConfigMerge.merge(new File(plugin.getAddonFolder().toPath() + File.separator + "lang/es.yml"),
                plugin.getResourceAsStream("Punishments/lang/es.yml")
        );

        ConfigMerge.merge(new File(plugin.getAddonFolder().toPath().toString() + File.separator + "lang/en.yml"),
                plugin.getResourceAsStream("Punishments/lang/en.yml")
        );

        es = new ConfigManager(plugin.getAddonFolder().toPath(), "Punishments/lang/es.yml", "/lang/es.yml");
        en = new ConfigManager(plugin.getAddonFolder().toPath(), "Punishments/lang/en.yml", "/lang/en.yml");
        en.registerConfig();
        es.registerConfig();
    }

    public static void loadMessage() {
        String language = config.getConfig().getString("Config.language");
        String lang = "lang/" + language;

        messages = new ConfigManager(plugin.getAddonFolder().toPath(), "/" + lang, lang);
        messages.registerConfig();
        try {
            messages.getConfig().load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConfigManager loadFile(String original, String path, MAStaffAddon<?> plugin) {
        ConfigMerge.merge(new File(plugin.getAddonFolder().toPath() + File.separator + path),
                plugin.getResourceAsStream(original)
        );

        ConfigManager config = new ConfigManager(plugin.getAddonFolder().toPath(), path, path);
        config.registerConfig();
        return config;
    }
}
