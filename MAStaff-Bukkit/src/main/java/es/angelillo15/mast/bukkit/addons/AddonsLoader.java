package es.angelillo15.mast.bukkit.addons;

import es.angelillo15.mast.api.addons.AddonDescription;
import es.angelillo15.mast.api.addons.AddonsManager;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.bukkit.MAStaff;
import lombok.SneakyThrows;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonsLoader {
    @SneakyThrows
    public static void loadAddons() {
        MAStaff.getPlugin().getPLogger().debug("Loading addons...");
        File addonsFolder = new File(MAStaff.getPlugin().getDataFolder() + File.separator + "addons");
        if (!addonsFolder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            addonsFolder.mkdir();
        }

        for (File file : Objects.requireNonNull(addonsFolder.listFiles())) {
            MAStaff.getPlugin().getPLogger().debug("Loading addon " + file.getName() + "...");

            if(file.isFile() && file.getName().endsWith(".jar")) {
                URL[] urls = new URL[]{file.toURI().toURL()};
                AddonDescription addonDescription = new AddonDescription();

                JarFile jarFile = new JarFile(file);
                Properties properties = new Properties();
                InputStream propertiesFile;

                JarEntry jarEntry = jarFile.getJarEntry("addon.properties");

                if (jarEntry == null) {
                    MAStaff.getPlugin().getPLogger().error("Addon " + file.getName() + " doesn't have a addon.properties file!");
                    return;
                }

                propertiesFile = jarFile.getInputStream(jarEntry);

                if (propertiesFile == null) {
                    MAStaff.getPlugin().getPLogger().error("Addon " + file.getName() + " doesn't have a addon.properties file!");
                }

                properties.load(propertiesFile);

                addonDescription.setName(properties.getProperty("name"));
                addonDescription.setMain(properties.getProperty("main"));
                addonDescription.setVersion(properties.getProperty("version"));
                addonDescription.setAuthor(properties.getProperty("author"));
                addonDescription.setDescription(
                        properties.getProperty("description") == null ? "No description"
                                : properties.getProperty("description")
                );

                @SuppressWarnings("resource")
                Class<?> cls = new URLClassLoader(urls, MAStaff.getPlugin().getClass().getClassLoader())
                        .loadClass(addonDescription.getMain());

                MAStaffAddon addon = (MAStaffAddon) cls.getDeclaredConstructor().newInstance();

                addon.init(new File(file.getParentFile() + File.separator + addonDescription.getName()), addonDescription, MAStaff.getPlugin());
                addon.onEnable();

                AddonsManager.registerAddon(addon);
                MAStaff.getPlugin().getPLogger().debug("Addon " + addonDescription.getName() + " Version: " + addonDescription.getVersion() + " loaded!");
            }
        }
        MAStaff.getPlugin().getPLogger().debug("Addons loaded!");
    }

    @SneakyThrows
    public static void disableAddons(){
        MAStaff.getPlugin().getPLogger().debug("Disabling addons...");
        for(MAStaffAddon addon : AddonsManager.getAddons().values()){
            MAStaff.getPlugin().getPLogger().debug("Disabling addon " + addon.getDescriptionFile().getName() + "...");
            addon.onDisable();
            MAStaff.getPlugin().getPLogger().debug("Addon " + addon.getDescriptionFile().getName() + " disabled!");
        }
        MAStaff.getPlugin().getPLogger().debug("Addons disabled!");
    }
}
