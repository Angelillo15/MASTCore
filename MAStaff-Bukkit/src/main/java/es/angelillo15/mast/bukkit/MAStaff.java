package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.utils.Logger;
import es.angelillo15.mast.bukkit.utils.TextUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MAStaff extends JavaPlugin implements MAStaffInstance {
    @Getter
    private static MAStaff plugin;
    private boolean debug = false;
    private static ILogger logger;

    @Override
    public void onEnable() {
        plugin = this;
        super.onEnable();
    }

    @Override
    public ILogger getPLogger() {
        return logger;
    }
    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void drawLogo() {
        logger = new Logger();
        logger.info(TextUtils.colorize("&a"));
        logger.info(TextUtils.colorize("&a ███▄ ▄███▓ ▄▄▄        ██████ ▄▄▄█████▓ ▄▄▄        █████▒ █████▒"));
        logger.info(TextUtils.colorize("&a ▓██▒▀█▀ ██▒▒████▄    ▒██    ▒ ▓  ██▒ ▓▒▒████▄    ▓██   ▒▓██   ▒"));
        logger.info(TextUtils.colorize("&a ▓██    ▓██░▒██  ▀█▄  ░ ▓██▄   ▒ ▓██░ ▒░▒██  ▀█▄  ▒████ ░▒████ ░"));
        logger.info(TextUtils.colorize("&a ▒██    ▒██ ░██▄▄▄▄██   ▒   ██▒░ ▓██▓ ░ ░██▄▄▄▄██ ░▓█▒  ░░▓█▒  ░"));
        logger.info(TextUtils.colorize("&a ▒██▒   ░██▒ ▓█   ▓██▒▒██████▒▒  ▒██▒ ░  ▓█   ▓██▒░▒█░   ░▒█░"));
        logger.info(TextUtils.colorize("&a ░ ▒░   ░  ░ ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░  ▒ ░░    ▒▒   ▓▒█░ ▒ ░    ▒ ░"));
        logger.info(TextUtils.colorize("&a ░  ░      ░  ▒   ▒▒ ░░ ░▒  ░ ░    ░      ▒   ▒▒ ░ ░      ░"));
        logger.info(TextUtils.colorize("&a ░      ░     ░   ▒   ░  ░  ░    ░        ░   ▒    ░ ░    ░ ░"));
        logger.info(TextUtils.colorize("&a ░         ░  ░      ░                 ░  ░"));
        logger.info(TextUtils.colorize("&a                                                version: " + getDescription().getVersion()));
    }

    @Override
    public void loadConfig() {
        new ConfigLoader().load();
        debug = ConfigLoader.getConfig().getConfig().getBoolean("Config.debug");
    }

    @Override
    public void registerCommands() {

    }

    @Override
    public void registerListeners() {

    }

    @Override
    public void loadDatabase() {

    }

    @Override
    public void loadModules() {

    }

    @Override
    public void unregisterCommands() {

    }

    @Override
    public void unregisterListeners() {

    }

    @Override
    public void unloadDatabase() {

    }

    @Override
    public void reload() {

    }
}
