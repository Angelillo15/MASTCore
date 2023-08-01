package es.angelillo15.mast.api.addons;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.config.ConfigManager;
import lombok.Getter;

import java.io.File;
import java.io.InputStream;

public class MAStaffAddon<P> {
    @Getter
    private File addonFolder;
    @Getter
    private AddonDescription descriptionFile;
    @Getter
    private ILogger logger;
    @Getter
    private ConfigManager config;
    @Getter
    private MAStaffInstance<?> mastaffInstance;
    @Getter
    private P pluginInstance;

    public void init(File addonFolder, AddonDescription descriptionFile, MAStaffInstance<?> mastaffInstance, P pluginInstance) {
        this.addonFolder = addonFolder;
        this.descriptionFile = descriptionFile;
        if (mastaffInstance != null) {
            this.mastaffInstance = mastaffInstance;
            this.logger = new AddonLogger(this, mastaffInstance.getPLogger());
        }

        this.pluginInstance = pluginInstance;
    }

    /**
     * Called when the addon is enabled
     * Needs to be implemented
     */
    public void onEnable() {
        // needs to be implemented
    }

    /**
     * Called when the addon is disabled
     * Needs to be implemented
     */
    public void onDisable() {
        // needs to be implemented
    }

    /**
     * Called when the addon is reloaded
     * Needs to be implemented
     */
    public void reload(){
        // needs to be implemented
        onEnable();
    }

    public void loadConfig() {
        if(!this.addonFolder.exists()) this.addonFolder.mkdirs();
        config = new ConfigManager(this.addonFolder.toPath(), "config.yml", "config.yml", this);
        config.registerConfig();
    }

    public InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}
