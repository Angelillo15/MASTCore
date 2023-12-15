package es.angelillo15.mast.bungee;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nookure.mast.api.addons.AddonManager;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.webhook.DiscordWebhooks;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import com.nookure.mast.api.cmd.Command;
import com.nookure.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.config.common.CommonConfig;
import es.angelillo15.mast.api.config.common.CommonConfigLoader;
import es.angelillo15.mast.api.config.common.CommonMessages;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.inject.StaticMembersInjector;
import es.angelillo15.mast.api.managers.LegacyUserDataManager;
import es.angelillo15.mast.api.redis.RedisEventManager;
import es.angelillo15.mast.api.redis.events.server.ServerConnectedEvent;
import es.angelillo15.mast.api.thread.AsyncThreadKt;
import es.angelillo15.mast.bungee.addons.AddonsLoader;
import es.angelillo15.mast.bungee.cmd.*;
import es.angelillo15.mast.bungee.cmd.mastb.MastParentCMD;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.config.ConfigLoader;
import es.angelillo15.mast.bungee.inject.BungeeInjector;
import es.angelillo15.mast.bungee.listener.CommandExecutor;
import es.angelillo15.mast.bungee.listener.CommandManagerHandler;
import es.angelillo15.mast.bungee.listener.OnStaffJoinLeaveQuit;
import es.angelillo15.mast.bungee.listener.StaffChangeEvent;
import es.angelillo15.mast.bungee.listener.redis.server.OnServer;
import es.angelillo15.mast.bungee.listener.redis.staff.OnStaffJoinLeave;
import es.angelillo15.mast.bungee.listener.redis.staff.OnStaffSwitch;
import es.angelillo15.mast.bungee.listener.redis.staff.OnStaffTalk;
import es.angelillo15.mast.bungee.listener.user.UserJoinListener;
import es.angelillo15.mast.bungee.manager.RedisManager;
import es.angelillo15.mast.bungee.utils.BungeeServerUtils;
import es.angelillo15.mast.bungee.utils.Logger;
import es.angelillo15.mast.cmd.HelpOP;
import com.nookure.mast.cmd.StaffChat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class MAStaff extends Plugin implements MAStaffInstance<Plugin> {
  @Getter
  private static MAStaff instance;
  @Getter
  private static CommonConfigLoader configLoader;
  private ILogger logger;
  @Setter
  private boolean debug;
  private IServerUtils serverUtils;
  private Injector injector;

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
  public void unregisterCommand(Command command) {
    CommandData data;

    try {
      data = command.getClass().getAnnotation(CommandData.class);
    } catch (Exception e) {
      logger.error("Error while unregistering command " + command.getClass().getName());
      return;
    }

    getProxy().getPluginManager().getCommands().forEach(cmd -> {
      if (cmd.getKey().equals(data.name())) {
        getProxy().getPluginManager().unregisterCommand(cmd.getValue());
      }
    });
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
    AsyncThreadKt.start();

  }

  @Override
  public void loadConfig() {
    new ConfigLoader(this).load();
    configLoader = injector.getInstance(CommonConfigLoader.class);
    configLoader.load();
  }

  @Override
  public void registerCommands() {
    registerCommand(injector.getInstance(InfoCMD.class));
    registerCommand(injector.getInstance(MastParentCMD.class));
    registerCommand(injector.getInstance(StaffChat.class));
    registerCommand(injector.getInstance(HelpOP.class));
  }

  @Override
  public void registerListeners() {
    getProxy().getPluginManager().registerListener(this, new StaffChangeEvent());
    getProxy().getPluginManager().registerListener(this, new UserJoinListener());
    getProxy().getPluginManager().registerListener(this, new CommandExecutor());
    getProxy().getPluginManager().registerListener(this, injector.getInstance(OnStaffJoinLeaveQuit.class));
    getProxy().getPluginManager().registerListener(this, injector.getInstance(CommandManagerHandler.class));
    getProxy().getPluginManager().registerListener(
        this,
        injector.getInstance(es.angelillo15.mast.bungee.listener.staffchat.OnStaffTalk.class)
    );
    if (Config.Redis.isEnabled()) registerRedisListeners();
  }

  public void registerRedisListeners() {
    RedisEventManager em = RedisEventManager.getInstance();
    em.registerListener(new OnServer());
    em.registerListener(new OnStaffJoinLeave());
    em.registerListener(new OnStaffSwitch());
    em.registerListener(new OnStaffTalk());
    ServerConnectedEvent event = new ServerConnectedEvent(Config.Redis.getServerName());

    RedisManager.sendEvent(event);
  }

  @SneakyThrows
  @Override
  public void loadDatabase() {
    new RedisManager().load();

    if (Config.Database.type().equalsIgnoreCase("MYSQL")) {
      new PluginConnection(
          Config.Database.host(),
          Config.Database.port(),
          Config.Database.database(),
          Config.Database.username(),
          Config.Database.password()
      );
    } else {
      new PluginConnection(getDataFolder().getPath());
    }

    PluginConnection.getStorm().runMigrations();

    DataManager.load();

    MAStaff.getInstance().getPLogger().info("Database loaded!");
  }

  @Override
  public void loadModules() {
    AddonsLoader.loadAddons(injector);
    AddonManager addonManager = getInjector().getInstance(AddonManager.class);

    File folder = new File(getDataFolder() + "/addons");
    if (folder.mkdir()) logger.debug("Created addons folder");
    try {
      addonManager.loadAddonsToClasspath(folder.toPath());
      addonManager.enableAddon(DiscordWebhooks.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    addonManager.enableAllAddonsFromTheClasspath();
  }

  @Override
  public void unregisterCommands() {
    ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
  }

  @Override
  public void unregisterListeners() {
    ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
  }

  @Override
  public void unloadDatabase() {
    try {
      PluginConnection.getConnection().close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @SneakyThrows
  @Override
  public void reload() {
    unloadDatabase();
    unregisterCommands();
    unregisterListeners();
    loadConfig();
    loadDatabase();
    registerCommands();
    registerListeners();
    AddonsLoader.reloadAddons();
    injector.getInstance(AddonManager.class).reloadAllAddons();
    PluginConnection.getStorm().runMigrations();
  }

  public void unloadAddons() {
    injector.getInstance(AddonManager.class).disableAllAddons();
  }

  @SuppressWarnings("deprecation")
  public void loadInjector() {
    this.injector = Guice.createInjector(new BungeeInjector());
    StaticMembersInjector.injectStatics(injector, LegacyUserDataManager.class);
    StaticMembersInjector.injectStatics(injector, CommonMessages.class);
    StaticMembersInjector.injectStatics(injector, CommonConfig.class);
  }

  @Override
  public IServerUtils getServerUtils() {
    return serverUtils;
  }

  @Override
  public Plugin getPluginInstance() {
    return null;
  }

  @Override
  public File getPluginDataFolder() {
    return getDataFolder();
  }

  @Override
  public InputStream getPluginResource(String s) {
    return getResourceAsStream(s);
  }

  @Override
  public Injector getInjector() {
    return this.injector;
  }

  @Override
  public Addon.AddonPlatform getPlatform() {
    return Addon.AddonPlatform.BUNGEECORD;
  }
}
