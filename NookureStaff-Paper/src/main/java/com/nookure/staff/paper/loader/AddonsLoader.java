package com.nookure.staff.paper.loader;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.addons.AddonManager;
import com.nookure.staff.api.util.AbstractLoader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonsLoader implements AbstractLoader {
  @Inject
  private NookureStaff instance;
  @Inject
  private AddonManager manager;
  @Inject
  private Logger logger;

  @Override
  public void load() {
    File scanFolder = new File(instance.getPluginDataFolder(), "addons");

    if (!scanFolder.exists()) {
      if (scanFolder.mkdirs()) {
        logger.debug("Created addons folder!");
      }
    }

    File[] folder = scanFolder.listFiles((file, name) -> {
      if (file.isDirectory()) {
        return false;
      }

      return name.endsWith(".jar");
    });

    if (folder == null) {
      return;
    }

    for (File file : folder) {
      loadAddon(file);
    }
  }

  private void loadAddon(File file) {
    Properties properties = new Properties();

    try {
      URL[] urls = new URL[]{file.toURI().toURL()};

      try (JarFile jarFile = new JarFile(file)) {
        JarEntry entry = jarFile.getJarEntry("addon.properties");
        properties.load(jarFile.getInputStream(entry));
      } catch (IOException e) {
        logger.severe("Could not load addon %s", file.getName());
        logger.severe("An error ocurred while reading the file, please check the file and try again");
        logger.severe(e);
        return;
      }

      Class<?> cls;

      try (URLClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader())) {
        cls = classLoader.loadClass(properties.getProperty("main"));
      } catch (IOException e) {
        logger.severe("Could not load the main class of addon %s", file.getName());
        logger.severe("Please check the file and try again");
        logger.severe(e);
        return;
      }

      manager.enableAddon(cls);

    } catch (MalformedURLException e) {
      logger.severe("Could not load addon %s", file.getName());
      logger.severe("An error ocurred while parsing the file URL, please check the file and try again");
      logger.severe(e);
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      logger.severe(e);
    }
  }
}
