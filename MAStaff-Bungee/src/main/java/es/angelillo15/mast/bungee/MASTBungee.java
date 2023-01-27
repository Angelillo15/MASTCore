package es.angelillo15.mast.bungee;

import es.angelillo15.mast.bungee.config.ConfigUpdater;
import es.angelillo15.mast.bungee.metrics.Metrics;
import es.angelillo15.mast.bungee.utils.LibsLoader;

public class MASTBungee extends MASTBungeeManager {
    @Override
    public void onEnable() {
        drawLogo();
        LibsLoader.loadLibs();
        new ConfigUpdater().update();
        registerEvents();
        registerConfig();
        registerCommands();
        new Metrics(this, 16548);
    }

    @Override
    public void onDisable() {

    }
}
