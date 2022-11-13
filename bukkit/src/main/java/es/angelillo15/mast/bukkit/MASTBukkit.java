package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.bukkit.metrics.Metrics;

public class MASTBukkit extends MASTBukkitManager {
    @Override
    public void onEnable() {
        drawLogo();
        loadConfig();
        databaseConnection();
        registerCommands();
        registerEvents();
        setupMessenger();
        setupModules();
        initializeGlowSupport();
        setupPermissions();
        new Metrics(this, 16548);
    }
    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        disconnectDatabase();
    }
}
