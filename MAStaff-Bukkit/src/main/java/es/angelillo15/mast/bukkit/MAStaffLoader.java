package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.managers.loader.ReflectionLoader;
import es.angelillo15.mast.bukkit.addons.AddonsLoader;
import es.angelillo15.mast.bukkit.utils.LibsLoader;

public class MAStaffLoader extends MAStaff {
    @Override
    public void onEnable() {
        super.onEnable();
        drawLogo();
        LibsLoader.loadLibs();
        loadConfig();
        registerCommands();
        registerListeners();
        loadDatabase();
        loadModules();
        AddonsLoader.loadAddons();
        checkUpdates();
        debugInfo();
        ReflectionLoader.loadAll();
        ReflectionLoader.loadBukkit();
    }

    @Override
    public void onDisable() {
        AddonsLoader.disableAddons();
        super.onDisable();
    }
}
