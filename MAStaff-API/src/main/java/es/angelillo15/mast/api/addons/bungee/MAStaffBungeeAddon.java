package es.angelillo15.mast.api.addons.bungee;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.AddonDescription;
import es.angelillo15.mast.api.addons.AddonLogger;
import es.angelillo15.mast.api.addons.config.ConfigManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

public class MAStaffBungeeAddon {
    @Getter
    private File addonFolder;
    @Getter
    private AddonDescription descriptionFile;
    @Getter
    private Plugin mastaffInstance;
    @Getter
    private ILogger logger;
    @Getter
    private ConfigManager config;

    public void init(File addonFolder, AddonDescription descriptionFile, Plugin mastaffInstance) {
        this.addonFolder = addonFolder;
        this.descriptionFile = descriptionFile;
        this.mastaffInstance = mastaffInstance;
        this.logger = new BungeeAddonLogger(this);
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void loadConfig() {
        if(!this.addonFolder.exists()) this.addonFolder.mkdirs();
        config = new ConfigManager(this.addonFolder.toPath(), "config.yml", "config.yml", this);
        config.registerConfig();
    }
}
