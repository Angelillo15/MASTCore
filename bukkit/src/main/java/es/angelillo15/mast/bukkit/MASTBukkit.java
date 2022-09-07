package es.angelillo15.mast.bukkit;

public class MASTBukkit extends MASTBukkitManager {
    @Override
    public void onEnable() {
        drawLogo();
        loadConfig();
        databaseConnection();
        registerCommands();
        registerEvents();
        setupMessenger();

    }
    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        disconnectDatabase();
    }
}
