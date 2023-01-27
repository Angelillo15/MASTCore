package es.angelillo15.mast.bukkit;

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
        debugInfo();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
