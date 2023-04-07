package es.angelillo15.mast.bungee;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.bungee.cmd.HelpopCMD;
import es.angelillo15.mast.bungee.cmd.MASTBReload;
import es.angelillo15.mast.bungee.cmd.StaffChat;
import es.angelillo15.mast.bungee.config.ConfigLoader;
import es.angelillo15.mast.bungee.listener.StaffChangeEvent;
import es.angelillo15.mast.bungee.listener.StaffJoinChange;
import es.angelillo15.mast.bungee.listener.StaffTalkEvent;
import es.angelillo15.mast.bungee.utils.Logger;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;

public class MAStaff extends Plugin implements MAStaffInstance<Plugin> {
    @Getter
    private static MAStaff instance;
    private ILogger logger;
    @Setter
    private boolean debug;
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
        instance = this;

        logger.info(TextUtils.simpleColorize("&a"));
        logger.info(TextUtils.simpleColorize("&a ███▄ ▄███▓ ▄▄▄        ██████ ▄▄▄█████▓ ▄▄▄        █████▒ █████▒"));
        logger.info(TextUtils.simpleColorize("&a ▓██▒▀█▀ ██▒▒████▄    ▒██    ▒ ▓  ██▒ ▓▒▒████▄    ▓██   ▒▓██   ▒"));
        logger.info(TextUtils.simpleColorize("&a ▓██    ▓██░▒██  ▀█▄  ░ ▓██▄   ▒ ▓██░ ▒░▒██  ▀█▄  ▒████ ░▒████ ░"));
        logger.info(TextUtils.simpleColorize("&a ▒██    ▒██ ░██▄▄▄▄██   ▒   ██▒░ ▓██▓ ░ ░██▄▄▄▄██ ░▓█▒  ░░▓█▒  ░"));
        logger.info(TextUtils.simpleColorize("&a ▒██▒   ░██▒ ▓█   ▓██▒▒██████▒▒  ▒██▒ ░  ▓█   ▓██▒░▒█░   ░▒█░"));
        logger.info(TextUtils.simpleColorize("&a ░ ▒░   ░  ░ ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░  ▒ ░░    ▒▒   ▓▒█░ ▒ ░    ▒ ░"));
        logger.info(TextUtils.simpleColorize("&a ░  ░      ░  ▒   ▒▒ ░░ ░▒  ░ ░    ░      ▒   ▒▒ ░ ░      ░"));
        logger.info(TextUtils.simpleColorize("&a ░      ░     ░   ▒   ░  ░  ░    ░        ░   ▒    ░ ░    ░ ░"));
        logger.info(TextUtils.simpleColorize("&a ░         ░  ░      ░                 ░  ░"));
        logger.info(TextUtils.simpleColorize("&a                                                version: " + getDescription().getVersion()));
    }

    @Override
    public void loadConfig() {
        new ConfigLoader(this).load();
    }

    @Override
    public void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new StaffChat());
        getProxy().getPluginManager().registerCommand(this, new MASTBReload());
        getProxy().getPluginManager().registerCommand(this, new HelpopCMD());
    }

    @Override
    public void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new StaffChangeEvent());
        getProxy().getPluginManager().registerListener(this, new StaffJoinChange());
        getProxy().getPluginManager().registerListener(this, new StaffTalkEvent());
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


    @Override
    public Plugin getPluginInstance() {
        return null;
    }
}
