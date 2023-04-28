package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.managers.loader.ReflectionLoader;
import es.angelillo15.mast.bukkit.addons.AddonsLoader;
import es.angelillo15.mast.bukkit.utils.LibsLoader;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import io.papermc.lib.PaperLib;

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
        Scheduler.execute(this::checkUpdates);
        debugInfo();
        ReflectionLoader.loadAll();
        ReflectionLoader.loadBukkit();
        PaperLib.suggestPaper(this);
    }

    @Override
    public void onDisable() {
        AddonsLoader.disableAddons();
        super.onDisable();
    }
}
