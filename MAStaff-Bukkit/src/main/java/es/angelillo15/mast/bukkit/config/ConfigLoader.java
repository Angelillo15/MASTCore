package es.angelillo15.mast.bukkit.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.managers.ConfigMerge;
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
    @Getter
    private static ConfigManager customItems;
    @Getter
    private static ConfigManager punishmentsGUI;
    private String language;

    public ConfigLoader() {
        this.plugin = MAStaff.getPlugin();
    }

    public void load() {
        ILogger logger = MAStaff.getPlugin().getPLogger();
        loadConfig();
        logger.debug("Loaded config");
        logger.debug("Loading languages...");
        loadLanguages();
        logger.debug("Loading messages...");
        loadMessage();
        logger.debug("Loading internal staff items...");
        loadInternal();
        logger.debug("Loading glow module...");
        loadGlowModule();
        logger.debug("Loading custom items...");
        loadCustomItems();
        logger.debug("Loading punishments GUI...");
        loadPunishmentsGUI();
        logger.debug("Configs loaded!");
    }

    public void loadConfig() {
        ConfigMerge.merge(new File(plugin.getDataFolder().toPath().toString() + File.separator + "config.yml"),
                plugin.getResource("Bukkit/config.yml")
        );

        config = new ConfigManager(plugin.getDataFolder().toPath(), "Bukkit/config.yml", "config.yml");
        config.registerConfig();

        MAStaff.getPlugin().setDebug(config.getConfig().getBoolean("Config.debug"));
    }

    public void loadInternal (){
        internalStaffItems = new ConfigManager(plugin.getDataFolder().toPath(), "Bukkit/modules/items/internal.yml", "/modules/items/internal.yml");
        internalStaffItems.registerConfig();
    }

    public void loadLanguages() {
        File file = new File(plugin.getDataFolder().toPath().toString() + File.separator + "lang");
        if (!file.exists()) {
            file.mkdirs();
        }

        es = new ConfigManager(plugin.getDataFolder().toPath(), "Bukkit/lang/spanish.yml", "/lang/spanish.yml");
        en = new ConfigManager(plugin.getDataFolder().toPath(), "Bukkit/lang/english.yml", "/lang/english.yml");
        es.registerConfig();
        en.registerConfig();

    }

    public void loadMessage() {
        language = config.getConfig().getString("Config.language");
        String lang = "lang/" + language;

        messages = new ConfigManager(plugin.getDataFolder().toPath(), "/Bukkit/" +lang, lang);
        messages.registerConfig();
        try {
            messages.getConfig().load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPunishmentsGUI(){
        punishmentsGUI = new ConfigManager(plugin.getDataFolder().toPath(), "Bukkit/modules/punishments/gui.yml", "/modules/punishments/gui.yml");
        punishmentsGUI.registerConfig();
    }

    public void loadCustomItems() {
        customItems = new ConfigManager(plugin.getDataFolder().toPath(), "Bukkit/modules/items/custom.yml", "/modules/items/custom.yml");
        customItems.registerConfig();
    }

    public void loadGlowModule(){
        glow = new ConfigManager(plugin.getDataFolder().toPath(), "Bukkit/modules/glow.yml", "/modules/glow.yml");
        glow.registerConfig();
    }
}
