package com.nookure.mast.bungee;

import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.managers.loader.ReflectionLoader;
import es.angelillo15.mast.api.thread.AsyncThreadKt;
import es.angelillo15.mast.api.utils.BungeeUtils;
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
        AsyncThreadKt.start();
        BungeeUtils.setAudienceBungee(this);
        loadInjector();
        loadConfig();
        loadDatabase();
        registerListeners();
        registerCommands();
        ReflectionLoader.loadBungee();
        ReflectionLoader.loadAll();
        loadModules();
        PluginConnection.getStorm().runMigrations();
        new Metrics(this, 16548);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        unloadDatabase();
        AddonsLoader.unloadAddons();
        unloadAddons();
    }
}
