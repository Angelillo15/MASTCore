package com.nookure.mast.addon;

import com.google.inject.AbstractModule;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.addons.AddonContainer;
import com.nookure.mast.api.addons.AddonLogger;
import com.nookure.mast.api.addons.annotations.AddonDataFolder;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.api.addons.annotations.PluginDataFolder;
import com.nookure.mast.api.addons.config.AddonConfig;
import es.angelillo15.mast.api.ILogger;

import java.io.File;

public class AddonCommonModule extends AbstractModule {
  private final AddonContainer addonContainer;
  private final Addon.AddonPlatform addonPlatform;
  private final File addonDataFolder;
  private final MAStaff instance;
  private final Class<?> addonClass;

  public AddonCommonModule(AddonContainer addonContainer, MAStaff instance, Class<?> addonClass) {
    this.addonContainer = addonContainer;
    this.addonPlatform = instance.getPlatform();
    this.instance = instance;
    File addonDataFolder = new File(instance.getPluginDataFolder(), addonContainer.getDescription().getID());

    if (!addonDataFolder.exists()) {
      addonDataFolder.mkdirs();
    }

    this.addonDataFolder = addonDataFolder;
    this.addonClass = addonClass;
  }

  @Override
  protected void configure() {
    bind(AddonContainer.class).toInstance(addonContainer);
    bind(Addon.AddonPlatform.class).toInstance(addonPlatform);
    bind(File.class).annotatedWith(AddonDataFolder.class).toInstance(addonDataFolder);
    bind(File.class).annotatedWith(PluginDataFolder.class).toInstance(instance.getPluginDataFolder());
    bind(ILogger.class)
        .annotatedWith(com.nookure.mast.api.addons.annotations.AddonLogger.class)
        .toInstance(new AddonLogger(addonContainer.getDescription().getID(), instance.getPLogger()));
    bind(AddonConfig.class).toInstance(new AddonConfig(addonDataFolder, addonClass.getClassLoader()));
    bind(AddonLogger.class).toInstance(new AddonLogger(addonContainer.getDescription().getID(), instance.getPLogger()));
  }
}
