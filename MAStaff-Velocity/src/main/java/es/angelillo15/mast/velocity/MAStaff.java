package es.angelillo15.mast.velocity;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.mast.api.addons.AddonManager;
import com.nookure.mast.api.addons.annotations.Addon;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import es.angelillo15.mast.api.*;
import com.nookure.mast.api.cmd.Command;
import com.nookure.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.config.common.CommonConfig;
import es.angelillo15.mast.api.config.common.CommonConfigLoader;
import es.angelillo15.mast.api.config.common.CommonMessages;
import es.angelillo15.mast.api.config.velocity.Config;
import es.angelillo15.mast.api.config.velocity.Messages;
import es.angelillo15.mast.api.config.velocity.VelocityConfig;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.inject.StaticMembersInjector;
import com.nookure.mast.api.manager.cmd.CommandVelocitySenderManager;
import es.angelillo15.mast.api.thread.AsyncThreadKt;
import es.angelillo15.mast.cmd.HelpOP;
import es.angelillo15.mast.cmd.StaffChat;
import es.angelillo15.mast.velocity.cmd.CustomCommand;
import es.angelillo15.mast.velocity.cmd.mastv.MastParent;
import es.angelillo15.mast.velocity.inject.VelocityInjector;
import es.angelillo15.mast.velocity.listeners.*;
import es.angelillo15.mast.velocity.listeners.staffchat.OnPlayerChat;
import es.angelillo15.mast.velocity.utils.LibsLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.slf4j.Logger;

@Plugin(
    id = "mastaff",
    name = "MAStaff Velocity",
    version = Constants.VERSION,
    description = "MAStaff Velocity module",
    authors = {"angelillo15"})
public class MAStaff implements MAStaffInstance<ProxyServer> {
  @Getter
  private static MAStaff instance;
  @Getter
  private final ProxyServer proxyServer;
  @Getter
  private final Logger Slf4jLogger;
  @Getter
  private final Path dataDirectory;
  ClassLoader classLoader = getClass().getClassLoader();
  @Getter
  private ILogger logger;
  @Getter
  private PluginConnection connection;
  @Getter
  private CommonConfigLoader commonConfigLoader;
  @Getter
  private VelocityConfig velocityConfig;
  private Injector injector;
  private boolean debug;

  @Inject
  public MAStaff(ProxyServer proxyServer, Logger Slf4jLogger, @DataDirectory Path dataDirectory) {
    instance = this;
    this.Slf4jLogger = Slf4jLogger;
    this.proxyServer = proxyServer;
    logger = new es.angelillo15.mast.velocity.utils.Logger();
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
    AsyncThreadKt.start();
    proxyServer.getChannelRegistrar().register(MinecraftChannelIdentifier.from("mastaff:staff"));

    logger.info("&aMAStaff &7v" + Constants.VERSION + " &ahas been loaded correctly!");
  }

  @Subscribe
  public void onProxyShutDown(ProxyShutdownEvent event) {
    unloadDatabase();
    injector.getInstance(AddonManager.class).disableAllAddons();
    logger.info("&aMAStaff &7v" + Constants.VERSION + " &ahas been unloaded correctly!");
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
  public void setDebug(boolean debug) {
    this.debug = debug;
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
    injector.getInstance(VelocityConfig.class).load();
    StaticMembersInjector.injectStatics(injector, Config.class);
    StaticMembersInjector.injectStatics(injector, Messages.class);
    StaticMembersInjector.injectStatics(injector, CommonMessages.class);
    StaticMembersInjector.injectStatics(
        injector, es.angelillo15.mast.api.config.common.CommonConfig.class);
    velocityConfig = injector.getInstance(VelocityConfig.class);
    velocityConfig.load();
    commonConfigLoader = injector.getInstance(CommonConfigLoader.class);
    commonConfigLoader.load();
  }

  @Override
  public void registerCommands() {
    if (CommonConfig.Helpop.INSTANCE.enabled()) registerCommand(injector.getInstance(HelpOP.class));
    if (CommonConfig.StaffChat.INSTANCE.enabled())
      registerCommand(injector.getInstance(StaffChat.class));

    registerCommand(injector.getInstance(MastParent.class));
  }

  @Override
  public void registerListeners() {
    proxyServer.getEventManager().register(this, injector.getInstance(OnStaffChange.class));
    proxyServer.getEventManager().register(this, injector.getInstance(OnPlayerJoin.class));
    proxyServer.getEventManager().register(this, injector.getInstance(CommandManagerHandler.class));
    proxyServer
        .getEventManager()
        .register(this, injector.getInstance(CommandBackendExecutor.class));
    proxyServer.getEventManager().register(this, injector.getInstance(OnStaffJoinLeaveQuit.class));
    proxyServer.getEventManager().register(this, injector.getInstance(OnPlayerChat.class));
  }

  @SneakyThrows
  @Override
  public void loadDatabase() {
    if (Config.Database.type().equalsIgnoreCase("MYSQL")) {
      new PluginConnection(
          Config.Database.host(),
          Config.Database.port(),
          Config.Database.database(),
          Config.Database.username(),
          Config.Database.password());
    } else {
      new PluginConnection(getPluginDataFolder().getPath());
    }

    PluginConnection.getStorm().runMigrations();

    DataManager.load();

    MAStaff.getInstance().getPLogger().info("Database loaded!");
  }

  @Override
  public void loadModules() {
    AddonManager addonManager = injector.getInstance(AddonManager.class);
    File folder = new File(dataDirectory.toString() + "/addons");
    if (folder.mkdir()) logger.debug("Created addons folder");

    try {
      addonManager.loadAddonsToClasspath(folder.toPath());
    } catch (IOException e) {
      getSlf4jLogger().error("Error while loading addons", e);
    }

    addonManager.enableAllAddonsFromTheClasspath();
  }

  @Override
  public void unregisterCommands() {
    proxyServer
        .getCommandManager()
        .getAliases()
        .forEach(
            s -> {
              val cmd = proxyServer.getCommandManager().getCommandMeta(s);

              if (cmd == null) return;

              if (cmd.getPlugin() != this) {
                return;
              }

              proxyServer.getCommandManager().unregister(cmd);
            });
  }

  @Override
  public void unregisterListeners() {
    proxyServer.getEventManager().unregisterListeners(this);
  }

  @SneakyThrows
  @Override
  public void unloadDatabase() {
    PluginConnection.getConnection().close();
  }

  @Override
  public void reload() {
    long start = System.currentTimeMillis();
    logger.debug("Reloading...");
    logger.debug("Unregistering listeners...");
    unregisterListeners();
    logger.debug("Unregistering commands...");
    unregisterListeners();
    logger.debug("Unloading database...");
    unloadDatabase();
    logger.debug("Loading config...");
    loadConfig();
    logger.debug("Loading database...");
    loadDatabase();
    logger.debug("Registering commands...");
    registerCommands();
    logger.debug("Registering listeners...");
    registerListeners();
    logger.debug("Reloading addons...");
    injector.getInstance(AddonManager.class).reloadAllAddons();
    logger.debug("Addons reloaded!");
    logger.debug("Reloading complete on " + (System.currentTimeMillis() - start) + "ms");
  }

  @Override
  public void registerCommand(Command command) {
    CommandData commandData;

    CommandManager commandManager = proxyServer.getCommandManager();

    try {
      commandData = command.getClass().getAnnotation(CommandData.class);
    } catch (Exception e) {
      MAStaff.getInstance()
          .getPLogger()
          .error(
              "Command " + command.getClass().getSimpleName() + " has no CommandData annotation!");
      return;
    }

    CommandMeta commandMeta =
        commandManager
            .metaBuilder(commandData.name())
            .aliases(commandData.aliases())
            .plugin(this)
            .build();

    CustomCommand customCommand = new CustomCommand(
        getLogger(),
        injector.getInstance(CommandVelocitySenderManager.class),
        command,
        commandData.permission()
    );

    commandManager.register(commandMeta, customCommand);
  }

  @Override
  public void unregisterCommand(Command command) {
    CommandData data;

    try {
      data = command.getClass().getAnnotation(CommandData.class);
    } catch (Exception e) {
      logger.error("Error while unregistering command " + command.getClass().getName());
      return;
    }

    proxyServer.getCommandManager().getAliases().forEach(s -> {
      val cmd = proxyServer.getCommandManager().getCommandMeta(s);

      if (cmd == null) return;

      if (cmd.getPlugin() != this) {
        return;
      }

      if (cmd.getAliases().contains(data.name())) {
        proxyServer.getCommandManager().unregister(cmd);
      }
    });
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
  public ProxyServer getPluginInstance() {
    return this.proxyServer;
  }

  @Override
  public Injector getInjector() {
    return injector;
  }

  @Override
  public Addon.AddonPlatform getPlatform() {
    return Addon.AddonPlatform.VELOCITY;
  }
}
