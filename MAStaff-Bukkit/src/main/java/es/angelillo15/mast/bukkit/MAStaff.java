package es.angelillo15.mast.bukkit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nookure.mast.api.addons.AddonManager;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.webhook.DiscordWebhooks;
import es.angelillo15.mast.api.*;
import com.nookure.mast.api.cmd.Command;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.config.common.CommonConfigLoader;
import es.angelillo15.mast.api.database.DataProvider;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.inject.StaticMembersInjector;
import es.angelillo15.mast.api.managers.LegacyStaffPlayersManagers;
import es.angelillo15.mast.api.managers.LegacyUserDataManager;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.api.nms.VersionSupport;
import es.angelillo15.mast.api.thread.AsyncThreadKt;
import es.angelillo15.mast.api.utils.BukkitUtils;
import es.angelillo15.mast.api.utils.PermsUtils;
import es.angelillo15.mast.bukkit.addons.AddonsLoader;
import es.angelillo15.mast.bukkit.cmd.FreezeCMD;
import es.angelillo15.mast.bukkit.cmd.VanishCMD;
import es.angelillo15.mast.bukkit.cmd.mast.MASTParent;
import es.angelillo15.mast.bukkit.cmd.staff.StaffParent;
import es.angelillo15.mast.bukkit.cmd.utils.CommandManager;
import es.angelillo15.mast.bukkit.cmd.utils.CommandTemplate;
import es.angelillo15.mast.bukkit.inject.BukkitInjector;
import es.angelillo15.mast.bukkit.legacy.BukkitLegacyLoader;
import es.angelillo15.mast.bukkit.listener.OnAddonDisable;
import es.angelillo15.mast.bukkit.listener.CommandManagerHandler;
import es.angelillo15.mast.bukkit.listener.FreezeListener;
import es.angelillo15.mast.bukkit.listener.OnJoin;
import es.angelillo15.mast.bukkit.listener.clickListeners.OnItemClick;
import es.angelillo15.mast.bukkit.listener.clickListeners.OnItemClickInteract;
import es.angelillo15.mast.bukkit.listener.staffchat.OnStaffLegacyTalk;
import es.angelillo15.mast.bukkit.listener.staffchat.OnStaffPaperTalk;
import es.angelillo15.mast.bukkit.listener.staffmode.*;
import es.angelillo15.mast.bukkit.listener.staffmode.achivement.OnAchievement;
import es.angelillo15.mast.bukkit.loaders.LegacyCustomItemsLoader;
import es.angelillo15.mast.bukkit.loaders.GlowLoader;
import es.angelillo15.mast.bukkit.loaders.LegacyItemsLoader;
import es.angelillo15.mast.bukkit.loaders.PunishmentGUILoader;
import es.angelillo15.mast.bukkit.utils.FreezeUtils;
import es.angelillo15.mast.bukkit.utils.Logger;
import es.angelillo15.mast.bukkit.utils.Metrics;
import es.angelillo15.mast.bukkit.utils.NMSUtils;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import es.angelillo15.mast.cmd.HelpOP;
import es.angelillo15.mast.cmd.StaffChat;
import es.angelillo15.mast.papi.MAStaffExtension;
import io.papermc.lib.PaperLib;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.SneakyThrows;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;

public class MAStaff extends JavaPlugin implements MAStaffInstance<JavaPlugin> {
  @Getter
  static final int version = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);
  @Getter
  static boolean isFree = false;
  private static MAStaff plugin;
  private static ILogger logger;
  @Getter
  private static PluginConnection pluginConnection;
  @Getter
  private static Connection connection;
  @Getter
  private static int currentVersion;
  @Getter
  private static int spiVersion;
  private AddonManager addonManager;
  private final ArrayList<Listener> listeners = new ArrayList<>();
  private boolean debug = false;
  private Injector injector;

  @Override
  public void onEnable() {
    plugin = this;
    Scheduler.init();
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
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public void setupMiniMessage() {
    BukkitUtils.setAudienceBukkit(this);
  }

  @Override
  public void drawLogo() {
    new Metrics(this, 16548);
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
    new ConfigLoader(this).load();
    injector.getInstance(CommonConfigLoader.class).load();
  }

  @Override
  public void registerCommands() {
    registerCommand(injector.getInstance(StaffParent.class));
    registerCommand(injector.getInstance(StaffChat.class));
    registerCommand(injector.getInstance(MASTParent.class));
    registerCommand(injector.getInstance(HelpOP.class));
    if (Config.Addons.vanish())
      registerCommand(injector.getInstance(VanishCMD.class));
    if (Config.Freeze.enabled())
      Objects.requireNonNull(getCommand("freeze")).setExecutor(injector.getInstance(FreezeCMD.class));
  }

  @Override
  public void registerListeners() {
    registerListener(injector.getInstance(OnJoin.class));
    registerListener(injector.getInstance(OnItemClick.class));
    registerListener(injector.getInstance(OnItemDrop.class));
    registerListener(injector.getInstance(OnInventoryClick.class));
    registerListener(injector.getInstance(OnItemClickInteract.class));
    registerListener(injector.getInstance(OnJoinLeave.class));
    registerListener(injector.getInstance(OnItemDrop.class));
    registerListener(injector.getInstance(OnEntityTarget.class));
    registerListener(injector.getInstance(OnFoodLevelChange.class));
    registerListener(injector.getInstance(OnItemGet.class));
    registerListener(injector.getInstance(OnPlayerInteractAtEntityEvent.class));
    registerListener(injector.getInstance(OnAttack.class));
    registerListener(injector.getInstance(OnDamage.class));
    registerListener(injector.getInstance(CommandManagerHandler.class));
    injector.getInstance(EventManager.class).registerListener(injector.getInstance(OnAddonDisable.class));

    if (Config.Freeze.enabled()) registerListener(injector.getInstance(FreezeListener.class));
    if (Config.silentOpenChest()) registerListener(injector.getInstance(OnOpenChest.class));

    if (version >= 19) registerListener(injector.getInstance(OnBlockReceiveGameEvent.class));
    if (version >= 9) registerListener(injector.getInstance(OnSwapHand.class));
    if (version >= 9 && PaperLib.isPaper()) registerListener(injector.getInstance(OnAchievement.class));

    if (version >= 16 && PaperLib.isPaper()) {
      registerListener(injector.getInstance(OnStaffPaperTalk.class));
    } else {
      registerListener(injector.getInstance(OnStaffLegacyTalk.class));
    }

    FreezeUtils.setupMessageSender();
    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "mastaff:staff");
    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "mastaff:commands");
  }

  public void registerListener(Listener listener) {
    listeners.add(listener);
    Bukkit.getPluginManager().registerEvents(listener, this);
  }

  @Override
  public void loadDatabase() {
    YamlFile config = ConfigLoader.getConfig().getConfig();
    DataProvider dataProvider = DataProvider.valueOf(config
        .getString("Database.type").toUpperCase()
    );
    try {
      switch (dataProvider) {
        case MYSQL -> pluginConnection = new PluginConnection(
            config.getString("Database.host"),
            config.getInt("Database.port"),
            config.getString("Database.database"),
            config.getString("Database.user"),
            config.getString("Database.password")
        );
        case SQLITE -> loadSqlite();
      }
      connection = PluginConnection.getConnection();
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.error("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
      logger.error("┃ An error ocurred while connecting to the MySQL database!                 ┃");
      logger.error("┃ Please, check your database credentials.                                 ┃");
      logger.error("┃ If you need help, join our Discord server to get support:                ┃");
      logger.error("┃ https://discord.nookure.com                                              ┃");
      logger.error("┃ The plugin is now connecting to a SQLite database...                     ┃");
      logger.error("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
      try {
        pluginConnection = new PluginConnection(
            getPlugin().getDataFolder().getAbsolutePath()
        );
      } catch (Exception e1) {
        logger.error(e1.getMessage());
      }
    }
    PluginConnection.getQueries().createTables();
    logger.info(TextUtils.colorize("&aConnected to the {type} database successfully.")
        .replace("{type}", PluginConnection.getDataProvider().name())
    );
  }

  public void loadSqlite() {
    pluginConnection = new PluginConnection(
        getPlugin().getDataFolder().getAbsolutePath()
    );
  }

  @SneakyThrows
  @Override
  public void loadModules() {
    addonManager = injector.getInstance(AddonManager.class);

    File folder = new File(getDataFolder() + "/addons");
    if (folder.mkdir()) logger.debug("Created addons folder");
    addonManager.loadAddonsToClasspath(folder.toPath());
    addonManager.enableAddon(DiscordWebhooks.class);
    addonManager.enableAllAddonsFromTheClasspath();

    LegacyItemsLoader.load();
    LegacyCustomItemsLoader.load();
    PunishmentGUILoader.load();
    new InventoryAPI(this).init();
    if (version < 9) {
      logger.info("Loading legacy modules...");
      new BukkitLegacyLoader().load(this);
    }
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return;
    }
    PermsUtils.setupPermissions();
  }

  @Override
  public void unregisterCommands() {
    if (CommandManager.getCommandMap() == null) return;

    CommandTemplate.Companion.getCommandMap().forEach((s, command) -> {
      CommandTemplate.Companion.unregisterCommand(s);
    });

  }

  @Override
  public void unregisterListeners() {
    HandlerList.getHandlerLists().forEach(listener -> {
      listeners.forEach(listener::unregister);
    });
    listeners.clear();
  }

  @Override
  @SneakyThrows
  public void unloadDatabase() {
    connection.close();
    logger.info(TextUtils.colorize("&aDisconnected from the {type} database successfully.")
        .replace("{type}", PluginConnection.getDataProvider().name())
    );
  }

  @Override
  public void reload() {
    long start = System.currentTimeMillis();
    logger.debug("Unloading Database...");
    unloadDatabase();
    logger.debug("Unregistering Commands...");
    unregisterCommands();
    logger.debug("Unregistering Listeners...");
    unregisterListeners();
    logger.debug("Stopping tasks...");
    AsyncThreadKt.stop();
    logger.debug("Reloading Config...");
    loadConfig();
    Messages.setMessages(ConfigLoader.getMessages().getConfig());
    logger.debug("Loading Database...");
    loadDatabase();
    logger.debug("Loading inventory API...");
    new InventoryAPI(this).init();
    logger.debug("Loading Glow...");
    GlowLoader.loadGlow();
    logger.debug("Loading items...");
    LegacyItemsLoader.load();
    LegacyCustomItemsLoader.load();
    logger.debug("Loading punishments GUI...");
    PunishmentGUILoader.load();
    logger.debug("Registering Commands...");
    registerCommands();
    logger.debug("Registering Listeners...");
    registerListeners();
    logger.debug("reloading addons...");
    AddonsLoader.reload();
    logger.debug("Checking for updates...");
    debugInfo();
    logger.debug("Starting tasks...");
    AsyncThreadKt.start();
    new Thread(this::checkUpdates);
    logger.debug("Reloading addons...");
    addonManager.reloadAllAddons();
    logger.debug("Addons reloaded successfully ✔️");
    long end = System.currentTimeMillis();
    logger.debug("Reloaded successfully in {time}ms ✔️"
        .replace("{time}", String.valueOf(end - start))
    );
  }

  public void debugInfo() {
    logger.debug("Debug info:");
    logger.debug("Server version: 1." + version);
    logger.debug("Plugin version: " + getDescription().getVersion());
    logger.debug("Plugin connection: " + PluginConnection.getDataProvider().name());
  }

  public void checkUpdates() {
    HttpResponse<String> response = Unirest.get("https://api.spigotmc.org/legacy/update.php?resource=105713")
        .asString();
    currentVersion = Integer.parseInt(Constants.VERSION
        .replace("-BETA", "")
        .replace("-DEV", "")
        .replace(".", "")
        .replace("v", "")
        .replace("V", "")
        .replace("b", "")
        .replace("B", "")
        .replace("-snapshot", "")
    );
    spiVersion = Integer.parseInt(response.getBody()
        .replace(".", "")
        .replace("v", "")
    );
    logger.debug("Current version: " + currentVersion);
    logger.debug("Spigot version: " + spiVersion);
    if (spiVersion > currentVersion) {
      logger.warn(TextUtils.colorize("&cThere is a new version available!"));
      return;
    }
    if (spiVersion == currentVersion) {
      logger.info(TextUtils.colorize("&aYou are using the latest version!"));
      return;
    }
    logger.warn(TextUtils.colorize("You are using a development version!"));
  }

  @SuppressWarnings("Deprecated")
  public void inject() {
    getPLogger().debug("Injecting...");
    injector = Guice.createInjector(new BukkitInjector());
    StaticMembersInjector.injectStatics(injector, LegacyStaffPlayersManagers.class);
    StaticMembersInjector.injectStatics(injector, LegacyUserDataManager.class);
  }

  public void registerPlaceholderAPI() {
    if (!MAStaffInstance.placeholderCheck()) return;
    getPLogger().info("PlaceholderAPI found! Registering placeholders...");
    injector.getInstance(MAStaffExtension.class).register();
  }

  public void disableAddons() {
    addonManager.disableAllAddons();
  }

  @Override
  public void registerCommand(Command command) {
    CommandTemplate.Companion.registerCommand(command);
  }

  @Override
  public void unregisterCommand(Command command) {
    CommandTemplate.Companion.unregisterCommand(command);
  }

  @Override
  public IServerUtils getServerUtils() {
    return injector.getInstance(ServerUtils.class);
  }

  @Override
  public IStaffPlayer createStaffPlayer(Player player) {
    if (!player.hasPermission("mast.staff")) return null;
    IStaffPlayer staffPlayer = injector.getInstance(StaffPlayer.class).setPlayer(player);
    StaffManager manager = injector.getInstance(StaffManager.class);
    manager.addStaffPlayer(staffPlayer);
    return staffPlayer;
  }

  @Override
  public JavaPlugin getPluginInstance() {
    return this;
  }

  @Override
  public File getPluginDataFolder() {
    return getDataFolder();
  }

  @Override
  public InputStream getPluginResource(String s) {
    return getResource(s);
  }

  @Override
  public Injector getInjector() {
    return injector;
  }

  @Override
  public VersionSupport getVersionSupport() {
    return NMSUtils.getVersionSupport();
  }

  public static MAStaff getPlugin() {
    return plugin;
  }

  @Override
  public Addon.AddonPlatform getPlatform() {
    return Addon.AddonPlatform.BUKKIT;
  }
}
