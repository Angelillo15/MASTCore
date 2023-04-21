package es.angelillo15.mast.api.punishments.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.managers.ConfigMerge;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

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

    public static void load() {

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
            file.mkdir();
        }

        ConfigMerge.merge(new File(plugin.getAddonFolder().toPath() + File.separator + "lang/spanish.yml"),
                plugin.getResourceAsStream("Punishments/lang/spanish.yml")
        );

        ConfigMerge.merge(new File(plugin.getAddonFolder().toPath().toString() + File.separator + "lang/english.yml"),
                plugin.getResourceAsStream("Punishments/lang/english.yml")
        );

        es = new ConfigManager(plugin.getAddonFolder().toPath(), "Punishments/lang/spanish.yml", "/lang/spanish.yml");
        en = new ConfigManager(plugin.getAddonFolder().toPath(), "Punishments/lang/english.yml", "/lang/english.yml");
        en.registerConfig();
        es.registerConfig();
    }

    public static void loadMessage() {
        String language = config.getConfig().getString("Config.language");
        String lang = "lang/" + language;

        messages = new ConfigManager(plugin.getAddonFolder().toPath(), "/Punishments/" + lang, lang);
        messages.registerConfig();
        try {
            messages.getConfig().load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
