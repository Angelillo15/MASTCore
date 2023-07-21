package es.angelillo15.mast.bungee.addons;

import com.google.inject.Injector;
import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.AddonDescription;
import es.angelillo15.mast.api.addons.AddonsManager;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.punishments.MAStaffPunishmentsLoader;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@SuppressWarnings("unchecked")
public class AddonsLoader {
    @SneakyThrows
    public static void loadAddons(Injector injector) {
        File addonsFolder = new File(MAStaff.getInstance().getDataFolder() + File.separator + "addons");
        if (!addonsFolder.exists()) {
            addonsFolder.mkdir();
        }

        loadDefaultAddons();

        for (File file : Objects.requireNonNull(addonsFolder.listFiles())) {
            if (!file.isFile() || !file.getName().endsWith(".jar")) {
                continue;
            }

            URL[] urls = new URL[]{file.toURI().toURL()};
            AddonDescription addonDescription = new AddonDescription();

            JarFile jarFile = new JarFile(file);
            Properties properties = new Properties();
            InputStream propertiesFile;

            JarEntry jarEntry = jarFile.getJarEntry("addon-bungee.properties");

            if (jarEntry == null) {
                MAStaff.getInstance().getLogger().severe("Addon " + file.getName() + " doesn't have a addon-bungee.properties file!");
                return;
            }

            propertiesFile = jarFile.getInputStream(jarEntry);

            if (propertiesFile == null) {
                MAStaff.getInstance().getLogger().severe("Addon " + file.getName() + " doesn't have a addon-bungee.properties file!");
            }

            properties.load(propertiesFile);

            addonDescription.setName(properties.getProperty("name"));
            addonDescription.setMain(properties.getProperty("main"));
            addonDescription.setVersion(properties.getProperty("version"));
            addonDescription.setAuthor(properties.getProperty("author"));

            Class<?> cls = new URLClassLoader(urls, MAStaff.getInstance().getClass().getClassLoader())
                    .loadClass(addonDescription.getMain());


            Object instance = injector.getInstance(cls);

            if (!(instance instanceof MAStaffInstance<?>)) {
                MAStaff.getInstance().getLogger().severe("Addon " + addonDescription.getName() + " v" + addonDescription.getVersion() + " by " + addonDescription.getAuthor() + " doesn't implement MAStaffInstance!");
                continue;
            }

            MAStaffAddon<Plugin> addon = (MAStaffAddon<Plugin>) instance;

            addon.init(new File(file.getParentFile() + File.separator + addonDescription.getName()), addonDescription, MAStaff.getInstance());
            try {
                addon.onEnable();
            } catch (Exception e) {
                MAStaff.getInstance().getLogger().severe("Error while enabling addon " + addonDescription.getName() + " v" + addonDescription.getVersion() + " by " + addonDescription.getAuthor());
                e.printStackTrace();
                continue;
            }


            AddonsManager.registerAddon(addon);
        }
    }

    public static void unloadAddons() {
        AddonsManager.getAddons().values().forEach(MAStaffAddon::onDisable);
    }

    public static void reloadAddons() {
        AddonsManager.getAddons().values().forEach(MAStaffAddon::reload);
    }

    public static void loadDefaultAddons() {
        if (Config.Modules.isPunishmentsEnabled())
            registerAddon("Punishments", MAStaff.getInstance().getInjector().getInstance(MAStaffPunishmentsLoader.class));
    }

    public static void registerAddon(AddonDescription addonDescription, MAStaffAddon<Plugin> addon) {
        addon.init(new File(MAStaff.getInstance().getDataFolder() + File.separator + "addons" + File.separator
                + addonDescription.getName()), addonDescription, MAStaff.getInstance());
        AddonsManager.registerAddon(addon);
        MAStaff.getInstance().getPLogger().debug("Registered addon " + addonDescription.getName() + " v" + addonDescription.getVersion() + " by " + addonDescription.getAuthor());

        try {
            addon.onEnable();
        } catch (Exception e) {
            MAStaff.getInstance().getLogger().severe("Error while enabling addon " + addonDescription.getName() + " v" + addonDescription.getVersion() + " by " + addonDescription.getAuthor());
            e.printStackTrace();
        }
    }

    public static void registerAddon(String name, MAStaffAddon<Plugin> addon) {
        AddonDescription addonDescription = new AddonDescription();
        addonDescription.setName(name);
        addonDescription.setVersion(Constants.VERSION);
        addonDescription.setAuthor("Angelillo15");
        addonDescription.setMain("Internal");

        registerAddon(addonDescription, addon);
    }

    public static void unregisterAddon(MAStaffAddon<Plugin> addon) {
        AddonsManager.unregisterAddon(addon);
    }
}
