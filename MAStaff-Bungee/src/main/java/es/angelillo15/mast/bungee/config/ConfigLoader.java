package es.angelillo15.mast.bungee.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.managers.ConfigMerge;
import es.angelillo15.mast.bungee.MAStaff;
import java.io.File;
import java.io.IOException;
import lombok.Getter;

public class ConfigLoader {
  @Getter private static ConfigManager config;
  @Getter private static ConfigManager messages;
  @Getter private static ConfigManager es;
  @Getter private static ConfigManager en;
  private MAStaff plugin;
  private String language;

  public ConfigLoader(MAStaff plugin) {
    this.plugin = plugin;
    ConfigMerge.loggerSetup();
  }

  public void load() {
    ILogger logger = MAStaff.getInstance().getPLogger();
    loadConfig();
    logger.debug("Loaded config");
    logger.debug("Loading languages...");
    loadLanguages();
    logger.debug("Loading messages...");
    loadMessage();
    logger.debug("Configs loaded!");
  }

  public void loadConfig() {
    ConfigMerge.merge(
        new File(plugin.getDataFolder().toPath().toString() + File.separator + "config.yml"),
        plugin.getResourceAsStream("BungeeCord/config.yml"));

    config =
        new ConfigManager(plugin.getDataFolder().toPath(), "BungeeCord/config.yml", "config.yml");
    config.registerConfig();

    MAStaff.getInstance().setDebug(config.getConfig().getBoolean("Config.debug"));
  }

  public void loadLanguages() {
    File file = new File(plugin.getDataFolder().toPath().toString() + File.separator + "lang");
    if (!file.exists()) {
      file.mkdir();
    }

    ConfigMerge.merge(
        new File(plugin.getDataFolder().toPath().toString() + File.separator + "lang/spanish.yml"),
        plugin.getResourceAsStream("BungeeCord/lang/spanish.yml"));

    ConfigMerge.merge(
        new File(plugin.getDataFolder().toPath().toString() + File.separator + "lang/english.yml"),
        plugin.getResourceAsStream("BungeeCord/lang/english.yml"));

    es =
        new ConfigManager(
            plugin.getDataFolder().toPath(), "BungeeCord/lang/spanish.yml", "/lang/spanish.yml");
    en =
        new ConfigManager(
            plugin.getDataFolder().toPath(), "BungeeCord/lang/english.yml", "/lang/english.yml");
    en.registerConfig();
    es.registerConfig();
  }

  public void loadMessage() {
    language = config.getConfig().getString("Config.language");
    String lang = "lang/" + language;

    messages = new ConfigManager(plugin.getDataFolder().toPath(), "/BungeeCord/" + lang, lang);
    messages.registerConfig();
    try {
      messages.getConfig().load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
