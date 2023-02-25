package es.angelillo15.mast.api.addons;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.config.ConfigManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class MAStaffAddon<T> {
    @Getter
    private File addonFolder;
    @Getter
    private AddonDescription descriptionFile;
    @Getter
    private T mastaffInstance;
    @Getter
    private ILogger logger;
    @Getter
    private ConfigManager config;

    public void init(File addonFolder, AddonDescription descriptionFile, T mastaffInstance) {
        this.addonFolder = addonFolder;
        this.descriptionFile = descriptionFile;
        this.mastaffInstance = mastaffInstance;
        this.logger = new AddonLogger(this, MAStaffInstance.getInstance().getPLogger());
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void reload(){

    }

    public void loadConfig() {
        if(!this.addonFolder.exists()) this.addonFolder.mkdirs();
        config = new ConfigManager(this.addonFolder.toPath(), "config.yml", "config.yml", this);
        config.registerConfig();
    }
}
