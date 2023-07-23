package es.angelillo15.mast.velocity;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import es.angelillo15.mast.api.*;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.config.common.CommonConfig;
import es.angelillo15.mast.api.config.velocity.Config;
import es.angelillo15.mast.api.config.velocity.ConfigLoader;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.cmd.HelpOP;
import es.angelillo15.mast.velocity.cmd.CustomCommand;
import es.angelillo15.mast.velocity.inject.VelocityInjector;
import es.angelillo15.mast.velocity.utils.LibsLoader;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

@Plugin(
        id = "mastaff",
        name = "MAStaff Velocity",
        version = Constants.VERSION,
        description = "MAStaff Velocity module",
        authors = {"angelillo15"}
)
public class MAStaff implements MAStaffInstance<ProxyServer> {
    @Getter
    private static MAStaff instance;
    @Getter
    private final ProxyServer proxyServer;
    @Getter
    private final Logger Slf4jLogger;
    @Getter
    private final Path dataDirectory;
    @Getter
    private ILogger logger;
    @Getter
    private PluginConnection connection;
    @Getter
    private CommonConfig commonConfig;
    private Injector injector;
    private boolean debug;
    ClassLoader classLoader = getClass().getClassLoader();

    @Inject
    public MAStaff(ProxyServer proxyServer, Logger Slf4jLogger, @DataDirectory Path dataDirectory) {
        instance = this;
        this.Slf4jLogger = Slf4jLogger;
        logger = new es.angelillo15.mast.velocity.utils.Logger();
        this.proxyServer = proxyServer;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        drawLogo();
        LibsLoader.load();
        injector = Guice.createInjector(new VelocityInjector());
        loadConfig();
        registerCommands();
        registerListeners();
        loadDatabase();
        loadModules();

        logger.info("&aMAStaff &7v" + Constants.VERSION + " &ahas been loaded correctly!");
    }

    @Override
    public ILogger getPLogger() {
        return logger;
    }

    @Override
    public IServerUtils getServerUtils() {
        return null;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void drawLogo() {
        logger.info("&a ███▄ ▄███▓ ▄▄▄        ██████ ▄▄▄█████▓ ▄▄▄        █████▒ █████▒");
        logger.info("&a ▓██▒▀█▀ ██▒▒████▄    ▒██    ▒ ▓  ██▒ ▓▒▒████▄    ▓██   ▒▓██   ▒");
        logger.info("&a ▓██    ▓██░▒██  ▀█▄  ░ ▓██▄   ▒ ▓██░ ▒░▒██  ▀█▄  ▒████ ░▒████ ░");
        logger.info("&a ▒██    ▒██ ░██▄▄▄▄██   ▒   ██▒░ ▓██▓ ░ ░██▄▄▄▄██ ░▓█▒  ░░▓█▒  ░");
        logger.info("&a ▒██▒   ░██▒ ▓█   ▓██▒▒██████▒▒  ▒██▒ ░  ▓█   ▓██▒░▒█░   ░▒█░");
        logger.info("&a ░ ▒░   ░  ░ ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░  ▒ ░░    ▒▒   ▓▒█░ ▒ ░    ▒ ░");
        logger.info("&a ░  ░      ░  ▒   ▒▒ ░░ ░▒  ░ ░    ░      ▒   ▒▒ ░ ░      ░");
        logger.info("&a ░      ░     ░   ▒   ░  ░  ░    ░        ░   ▒    ░ ░    ░ ░");
        logger.info("&a ░         ░  ░      ░                 ░  ░");
        logger.info("&a                                                version: " + Constants.VERSION);
    }

    @Override
    public void loadConfig() {
        new ConfigLoader(dataDirectory, this).load();
        commonConfig = injector.getInstance(CommonConfig.class);
        commonConfig.load();
    }

    @Override
    public void registerCommands() {
        registerCommand(injector.getInstance(HelpOP.class));
    }

    @Override
    public void registerListeners() {

    }

    @SneakyThrows
    @Override
    public void loadDatabase() {
        if (Config.Database.type().equalsIgnoreCase("MYSQL")) {
            new PluginConnection(Config.Database.host(), Config.Database.port(), Config.Database.database(), Config.Database.username(), Config.Database.password());
        } else {
            new PluginConnection(getPluginDataFolder().getPath());
        }

        PluginConnection.getStorm().runMigrations();

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
    public void registerCommand(Command command) {
        CommandData commandData = null;

        CommandManager commandManager = proxyServer.getCommandManager();

        try {
            commandData = command.getClass().getAnnotation(CommandData.class);
        } catch (Exception e) {
            MAStaff.getInstance().getPLogger().error("Command " + command.getClass().getSimpleName() + " has no CommandData annotation!");
            return;
        }

        CommandMeta commandMeta = commandManager.metaBuilder(commandData.name())
                .aliases(commandData.aliases())
                .plugin(this)
                .build();

        CustomCommand customCommand = new CustomCommand(command, commandData.permission());

        commandManager.register(commandMeta, customCommand);
    }

    @Override
    public File getPluginDataFolder() {
        return dataDirectory.toFile();
    }

    @Override
    public InputStream getPluginResource(String s) {
        return classLoader.getResourceAsStream(s);
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public ProxyServer getPluginInstance() {
        return this.proxyServer;
    }

    @Override
    public Injector getInjector() {
        return injector;
    }
}
