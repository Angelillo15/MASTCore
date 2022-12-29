package es.angelillo15.mast.bukkit.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.bukkit.MAStaff;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private MAStaff plugin;
    @Getter
    private static ConfigManager config;
    @Getter
    private static ConfigManager messages;
    @Getter
    private static ConfigManager internalStaffItems;
    @Getter
    private static ConfigManager glow;
    @Getter
    private static ConfigManager es;
    @Getter
    private static ConfigManager en;
    private String language;

    public ConfigLoader() {
        this.plugin = MAStaff.getPlugin();
    }

    public void load() {
        loadConfig();
        loadLanguages();
        loadMessage();
        loadInternal();
        //  loadGlowModule();
    }

    public void loadConfig() {
        config = new ConfigManager(plugin.getDataFolder().toPath(), "config.yml", "config.yml");
        config.registerConfig();
    }

    public void loadInternal (){
        internalStaffItems = new ConfigManager(plugin.getDataFolder().toPath(), "modules/internal.yml", "/modules/internal.yml");
        internalStaffItems.registerConfig();
    }

    public void loadLanguages() {
        File file = new File(plugin.getDataFolder().toPath().toString() + File.separator + "lang");
        if (!file.exists()) {
            file.mkdirs();
        }

        es = new ConfigManager(plugin.getDataFolder().toPath(), "lang/spanish.yml", "/lang/spanish.yml");
        en = new ConfigManager(plugin.getDataFolder().toPath(), "lang/english.yml", "/lang/english.yml");
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

    public void loadGlowModule(){
        glow = new ConfigManager(plugin.getDataFolder().toPath(), "modules/glow.yml", "/modules/glow.yml");
        glow.registerConfig();
    }
}
