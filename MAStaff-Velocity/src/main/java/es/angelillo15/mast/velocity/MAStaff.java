package es.angelillo15.mast.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import es.angelillo15.mast.api.*;
import es.angelillo15.mast.api.config.velocity.ConfigLoader;
import es.angelillo15.mast.velocity.utils.LibsLoader;
import lombok.Getter;
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
    private boolean debug;
    ClassLoader classLoader = getClass().getClassLoader();

    @Inject
    public MAStaff(ProxyServer proxyServer, Logger Slf4jLogger, @DataDirectory Path dataDirectory) {
        instance = this;
        logger = new es.angelillo15.mast.velocity.utils.Logger();

        this.proxyServer = proxyServer;
        this.Slf4jLogger = Slf4jLogger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        drawLogo();
        LibsLoader.load();
        loadConfig();
        registerCommands();
        registerListeners();
        loadDatabase();
        loadModules();
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
}
