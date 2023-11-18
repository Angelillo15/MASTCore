package com.nookure.mast.api.addons.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.api.managers.ConfigMerge;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;

public class AddonConfig {
  private final File folder;
  private final ClassLoader classLoader;

  public AddonConfig(File folder, ClassLoader classLoader) {
    this.folder = folder;
    this.classLoader = classLoader;
  }

  /**
   * This methods will load the default config.yml file
   * from the addon's classloader and merge
   * it with the file in the addon's data folder.
   * @return The ConfigManager instance.
   */
  @Nullable
  public ConfigManager getDefaultConfig() {
    if (getResource("config.yml") == null) {
      return null;
    }

    if (!folder.exists()) {
      folder.mkdirs();
    }

    return loadFile("config.yml", "config.yml");
  }

  /**
   * Loads a file from the addon's classloader and merges it with the file in the addon's data folder.
   * @param original The original file in the addon's classloader.
   * @param path The path to the file in the addon's data folder.
   * @return The ConfigManager instance.
   */
  @Nullable
  public ConfigManager loadFile(String original, String path) {
    ConfigMerge.merge(new File(folder, path), getResource(original));

    ConfigManager configManager = new ConfigManager(folder.toPath(), path, path);
    configManager.registerConfig();

    return configManager;
  }

  /**
   * Gets a resource from the addon's classloader.
   * @param name The name of the resource.
   * @return The resource as an InputStream.
   */
  @Nullable
  public InputStream getResource(String name) {
    return classLoader.getResourceAsStream(name);
  }
}
