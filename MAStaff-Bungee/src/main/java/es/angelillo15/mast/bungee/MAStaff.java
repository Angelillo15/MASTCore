package es.angelillo15.mast.bungee;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.redis.EventManager;
import es.angelillo15.mast.api.redis.events.server.ServerConnectedEvent;
import es.angelillo15.mast.bungee.cmd.*;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.config.ConfigLoader;
import es.angelillo15.mast.bungee.listener.StaffChangeEvent;
import es.angelillo15.mast.bungee.listener.StaffJoinChange;
import es.angelillo15.mast.bungee.listener.StaffTalkEvent;
import es.angelillo15.mast.bungee.listener.redis.server.OnServer;
import es.angelillo15.mast.bungee.listener.redis.staff.OnStaffJoinLeave;
import es.angelillo15.mast.bungee.listener.redis.staff.OnStaffSwitch;
import es.angelillo15.mast.bungee.listener.redis.staff.OnStaffTalk;
import es.angelillo15.mast.bungee.listener.user.UserJoinListener;
import es.angelillo15.mast.bungee.manager.RedisManager;
import es.angelillo15.mast.bungee.utils.BungeeServerUtils;
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
    private IServerUtils serverUtils;
    @Override
    public ILogger getPLogger() {
        return logger;
    }

    @Override
    public void registerCommand(Command command) {
        CommandData data;

        try {
            data = command.getClass().getAnnotation(CommandData.class);
        } catch (Exception e) {
            logger.error("Error while registering command " + command.getClass().getName());
            return;
        }

        if (data.aliases().length == 0 && data.permission().isEmpty()) {
            getProxy().getPluginManager().registerCommand(this, new CustomCommand(data.name(), command));
            return;
        }

        if (data.aliases().length == 0) {
            getProxy().getPluginManager().registerCommand(this, new CustomCommand(data.name(), data.permission(), command));
            return;
        }

        if (data.permission().isEmpty()) {
            getProxy().getPluginManager().registerCommand(this, new CustomCommand(data.name(), command, data.aliases()));
            return;
        }

        getProxy().getPluginManager().registerCommand(this, new CustomCommand(data.name(), data.permission(), command, data.aliases()));
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void drawLogo() {
        instance = this;
        logger = new Logger();
        serverUtils = new BungeeServerUtils();

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
        registerCommand(new InfoCMD());
    }

    @Override
    public void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new StaffChangeEvent());
        getProxy().getPluginManager().registerListener(this, new StaffJoinChange());
        getProxy().getPluginManager().registerListener(this, new StaffTalkEvent());
        getProxy().getPluginManager().registerListener(this, new UserJoinListener());
        if (Config.Redis.isEnabled()) registerRedisListeners();

    }

    public void registerRedisListeners() {
        EventManager em = EventManager.getInstance();
        em.registerListener(new OnServer());
        em.registerListener(new OnStaffJoinLeave());
        em.registerListener(new OnStaffSwitch());
        em.registerListener(new OnStaffTalk());
        ServerConnectedEvent event = new ServerConnectedEvent(Config.Redis.getServerName());
        RedisManager.sendEvent(event);
    }

    @Override
    public void loadDatabase() {
        new RedisManager().load();

        if (Config.Database.type().equalsIgnoreCase("MYSQL")) {
            new PluginConnection(Config.Database.host(), Config.Database.port(), Config.Database.database(), Config.Database.username(), Config.Database.password());
        } else {
            new PluginConnection(getDataFolder().getPath());
        }

        DataManager.load();

        MAStaff.getInstance().getPLogger().info("Database loaded!");
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
    public IServerUtils getServerUtils() {
        return serverUtils;
    }

    @Override
    public Plugin getPluginInstance() {
        return null;
    }
}
