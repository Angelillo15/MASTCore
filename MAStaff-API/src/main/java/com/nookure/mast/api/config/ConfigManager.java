package com.nookure.mast.api.config;

import com.nookure.mast.api.addons.config.AddonConfig;

import java.io.File;

public class ConfigManager extends AddonConfig {
  public ConfigManager(File folder, ClassLoader classLoader) {
    super(folder, classLoader);
  }
}
