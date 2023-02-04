package es.angelillo15.mast.api.addons;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class MAStaffAddon {
    @Getter
    private File addonFolder;
    @Getter
    private AddonDescription descriptionFile;
    @Getter
    private Plugin mastaffInstance;

    public void init(File addonFolder, AddonDescription descriptionFile, Plugin mastaffInstance) {
        this.addonFolder = addonFolder;
        this.descriptionFile = descriptionFile;
        this.mastaffInstance = mastaffInstance;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }
}
