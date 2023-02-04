package es.angelillo15.mast.bungee.addons;

import es.angelillo15.mast.api.addons.AddonDescription;
import es.angelillo15.mast.api.addons.AddonsManager;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.addons.bungee.BungeeAddonsManager;
import es.angelillo15.mast.api.addons.bungee.MAStaffBungeeAddon;
import es.angelillo15.mast.bungee.MASTBungee;
import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonsLoader {
    @SneakyThrows
    public static void loadAddons() {
        File addonsFolder = new File(MASTBungee.getInstance().getDataFolder() + File.separator + "addons");
        if (!addonsFolder.exists()) {
            addonsFolder.mkdir();
        }

        for (File file : addonsFolder.listFiles()) {
            if(file.isFile() && file.getName().endsWith(".jar")) {
                URL[] urls = new URL[]{file.toURI().toURL()};
                AddonDescription addonDescription = new AddonDescription();

                JarFile jarFile = new JarFile(file);
                Properties properties = new Properties();
                InputStream propertiesFile;

                JarEntry jarEntry = jarFile.getJarEntry("addon.properties");

                if (jarEntry == null) {
                    MASTBungee.getInstance().getLogger().severe("Addon " + file.getName() + " doesn't have a addon.properties file!");
                    return;
                }

                propertiesFile = jarFile.getInputStream(jarEntry);

                if (propertiesFile == null) {
                    MASTBungee.getInstance().getLogger().severe("Addon " + file.getName() + " doesn't have a addon.properties file!");
                }

                properties.load(propertiesFile);

                addonDescription.setName(properties.getProperty("name"));
                addonDescription.setMain(properties.getProperty("main"));
                addonDescription.setVersion(properties.getProperty("version"));
                addonDescription.setAuthor(properties.getProperty("author"));

                Class cls = new URLClassLoader(urls, MASTBungee.getInstance().getClass().getClassLoader())
                        .loadClass(addonDescription.getMain());

                MAStaffBungeeAddon addon = (MAStaffBungeeAddon) cls.newInstance();

                addon.init(new File(file.getParentFile() + File.separator + addonDescription.getName()), addonDescription, MASTBungee.getInstance());
                addon.onEnable();

                BungeeAddonsManager.registerAddon(addon);
            }
        }
    }

    public static void unloadAddons() {
        for (MAStaffAddon addon : AddonsManager.getAddons().values()) {
            addon.onDisable();
        }
    }
}
