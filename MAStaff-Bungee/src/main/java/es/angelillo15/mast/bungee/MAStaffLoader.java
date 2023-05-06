package es.angelillo15.mast.bungee;

import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.managers.loader.ReflectionLoader;
import es.angelillo15.mast.bungee.addons.AddonsLoader;
import es.angelillo15.mast.bungee.metrics.Metrics;
import es.angelillo15.mast.bungee.utils.LibsLoader;
import lombok.SneakyThrows;

public class MAStaffLoader extends MAStaff {
    @SneakyThrows
    @Override
    public void onEnable() {
        super.onEnable();
        drawLogo();
        LibsLoader.loadLibs();
        loadConfig();
        loadDatabase();
        registerListeners();
        registerCommands();
        ReflectionLoader.loadBungee();
        ReflectionLoader.loadAll();
        AddonsLoader.loadAddons();
        PluginConnection.getStorm().runMigrations();
        new Metrics(this, 16548);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        AddonsLoader.unloadAddons();
    }
}
