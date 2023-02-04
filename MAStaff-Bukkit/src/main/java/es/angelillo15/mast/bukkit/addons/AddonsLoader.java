package es.angelillo15.mast.bukkit.addons;

import es.angelillo15.mast.api.addons.AddonDescription;
import es.angelillo15.mast.api.addons.AddonsManager;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.bukkit.MAStaff;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarFile;

public class AddonsLoader {
    @SneakyThrows
    public void loadAddons() {
        File addonsFolder = new File(MAStaff.getPlugin().getDataFolder() + File.separator + "addons");
        if (!addonsFolder.exists()) {
            addonsFolder.mkdir();
        }

        for (File file : addonsFolder.listFiles()) {
            if(file.isDirectory()) return;
            if(!file.getName().endsWith(".jar")) return;
            URL[] urls = new URL[]{file.toURI().toURL()};
            AddonDescription addonDescription = new AddonDescription();

            if(file.getName().contains(" ")) return;
            JarFile jarFile = new JarFile(file);
            Properties properties = new Properties();

            properties.load(jarFile.getInputStream(jarFile.getJarEntry("addon.properties")));

            addonDescription.setName(properties.getProperty("name"));
            addonDescription.setMain(properties.getProperty("main"));
            addonDescription.setVersion(properties.getProperty("version"));
            addonDescription.setAuthor(properties.getProperty("author"));

            Class cls = new URLClassLoader(urls).loadClass(addonDescription.getMain());

            MAStaffAddon addon = (MAStaffAddon) cls.newInstance();

            addon.init(new File(file + File.separator + addonDescription.getName()), addonDescription, MAStaff.getPlugin());
            addon.onEnable();

            AddonsManager.registerAddon(addon);
        }
    }
}
