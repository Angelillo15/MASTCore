package es.angelillo15.mast.bukkit.addons;

import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.AddonDescription;
import es.angelillo15.mast.api.addons.LegacyAddonsManager;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.glow.GlowAddon;
import es.angelillo15.mast.vanish.MAStaffVanish;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("resource")
public class AddonsLoader {
  @SneakyThrows
  public static void loadAddons() {
    MAStaff.getPlugin().getPLogger().debug("Loading addons...");
    File addonsFolder = new File(MAStaff.getPlugin().getDataFolder() + File.separator + "addons");
    if (!addonsFolder.exists()) {
      //noinspection ResultOfMethodCallIgnored
      addonsFolder.mkdir();
    }

    loadDefaultAddons();

    for (File file : Objects.requireNonNull(addonsFolder.listFiles())) {
      MAStaff.getPlugin().getPLogger().debug("Loading addon " + file.getName() + "...");

      if (file.isFile() && file.getName().endsWith(".jar")) {
        URL[] urls = new URL[] {file.toURI().toURL()};
        AddonDescription addonDescription = new AddonDescription();

        JarFile jarFile = new JarFile(file);
        Properties properties = new Properties();
        InputStream propertiesFile;

        JarEntry jarEntry = jarFile.getJarEntry("addon.properties");

        if (jarEntry == null) {
          MAStaff.getPlugin()
              .getPLogger()
              .error("Addon " + file.getName() + " doesn't have a addon.properties file!");
          continue;
        }

        propertiesFile = jarFile.getInputStream(jarEntry);

        if (propertiesFile == null) {
          MAStaff.getPlugin()
              .getPLogger()
              .error("Addon " + file.getName() + " doesn't have a addon.properties file!");
          continue;
        }

        properties.load(propertiesFile);

        addonDescription.setName(properties.getProperty("name"));
        addonDescription.setMain(properties.getProperty("main"));
        addonDescription.setVersion(properties.getProperty("version"));
        addonDescription.setAuthor(properties.getProperty("author"));
        addonDescription.setDescription(
            properties.getProperty("description") == null
                ? "No description"
                : properties.getProperty("description"));

        Class<?> cls =
            new URLClassLoader(urls, MAStaff.getPlugin().getClass().getClassLoader())
                .loadClass(addonDescription.getMain());

        MAStaffAddon<JavaPlugin> addon =
            (MAStaffAddon<JavaPlugin>) cls.getDeclaredConstructor().newInstance();

        addon.init(
            new File(file.getParentFile() + File.separator + addonDescription.getName()),
            addonDescription,
            MAStaff.getPlugin(),
            MAStaff.getPlugin());
        addon.onEnable();

        LegacyAddonsManager.registerAddon(addon);
        MAStaff.getPlugin()
            .getPLogger()
            .debug(
                "Addon "
                    + addonDescription.getName()
                    + " Version: "
                    + addonDescription.getVersion()
                    + " loaded!");
      }
    }
    MAStaff.getPlugin().getPLogger().debug("Addons loaded!");
  }

  @SneakyThrows
  public static void disableAddons() {
    MAStaff.getPlugin().getPLogger().debug("Disabling addons...");
    for (MAStaffAddon<?> addon : LegacyAddonsManager.getAddons().values()) {
      MAStaff.getPlugin()
          .getPLogger()
          .debug("Disabling addon " + addon.getDescriptionFile().getName() + "...");
      addon.onDisable();
      MAStaff.getPlugin()
          .getPLogger()
          .debug("Addon " + addon.getDescriptionFile().getName() + " disabled!");
    }
    MAStaff.getPlugin().getPLogger().debug("Addons disabled!");
  }

  public static void reload() {
    MAStaff.getPlugin().getPLogger().debug("Reloading addons...");
    for (MAStaffAddon<?> addon : LegacyAddonsManager.getAddons().values()) {
      MAStaff.getPlugin()
          .getPLogger()
          .debug("Reloading addon " + addon.getDescriptionFile().getName() + "...");
      addon.reload();
      MAStaff.getPlugin()
          .getPLogger()
          .debug("Addon " + addon.getDescriptionFile().getName() + " reloaded!");
    }
    MAStaff.getPlugin().getPLogger().debug("Addons reloaded!");
  }

  public static void loadDefaultAddons() {
    loadDefaultAddons(false);
  }

  public static void loadDefaultAddons(boolean isFree) {
    if (Config.Addons.vanish()) registerAddon("Vanish", new MAStaffVanish());

    if (isFree) return;

    if (Config.Addons.glow() && MAStaffInstance.version() > 9)
      registerAddon("Glow", MAStaff.getPlugin().getInjector().getInstance(GlowAddon.class));
  }

  public static void registerAddon(
      AddonDescription addonDescription, MAStaffAddon<JavaPlugin> addon) {
    addon.init(
        new File(
            MAStaff.getPlugin().getDataFolder()
                + File.separator
                + "addons"
                + File.separator
                + addonDescription.getName()),
        addonDescription,
        MAStaff.getPlugin(),
        MAStaff.getPlugin());
    LegacyAddonsManager.registerAddon(addon);
    MAStaff.getPlugin()
        .getPLogger()
        .debug(
            "Registered addon "
                + addonDescription.getName()
                + " v"
                + addonDescription.getVersion()
                + " by "
                + addonDescription.getAuthor());

    try {
      addon.onEnable();
    } catch (Exception e) {
      MAStaff.getPlugin()
          .getLogger()
          .severe(
              "Error while enabling addon "
                  + addonDescription.getName()
                  + " v"
                  + addonDescription.getVersion()
                  + " by "
                  + addonDescription.getAuthor());
      e.printStackTrace();
    }
  }

  public static void registerAddon(String name, MAStaffAddon<JavaPlugin> addon) {
    AddonDescription addonDescription = new AddonDescription();
    addonDescription.setName(name);
    addonDescription.setVersion(Constants.VERSION);
    addonDescription.setAuthor("Angelillo15");
    addonDescription.setMain("Internal");

    registerAddon(addonDescription, addon);
  }
}
