package es.angelillo15.mast.bukkit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import es.angelillo15.mast.api.*;
import es.angelillo15.mast.api.database.DataProvider;
import es.angelillo15.mast.api.inject.StaticMembersInjector;
import es.angelillo15.mast.api.managers.LegacyUserDataManager;
import es.angelillo15.mast.api.managers.LegacyStaffPlayersManagers;
import es.angelillo15.mast.api.thread.AsyncThreadKt;
import es.angelillo15.mast.api.utils.BukkitUtils;
import es.angelillo15.mast.bukkit.addons.AddonsLoader;
import es.angelillo15.mast.bukkit.cmd.FreezeCMD;
import es.angelillo15.mast.bukkit.cmd.StaffChatCMD;
import es.angelillo15.mast.bukkit.cmd.mast.MAStaffCMD;
import es.angelillo15.mast.bukkit.cmd.staff.StaffCMD;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.bukkit.inject.BukkitInjector;
import es.angelillo15.mast.bukkit.legacy.BukkitLegacyLoader;
import es.angelillo15.mast.bukkit.listener.FreezeListener;
import es.angelillo15.mast.bukkit.listener.clickListeners.OnItemClick;
import es.angelillo15.mast.bukkit.listener.OnJoin;
import es.angelillo15.mast.bukkit.listener.clickListeners.OnItemClickInteract;
import es.angelillo15.mast.bukkit.listener.staffmode.*;
import es.angelillo15.mast.bukkit.listener.staffmode.achivement.OnAchievement;
import es.angelillo15.mast.bukkit.loaders.CustomItemsLoader;
import es.angelillo15.mast.bukkit.loaders.GlowLoader;
import es.angelillo15.mast.bukkit.loaders.ItemsLoader;
import es.angelillo15.mast.bukkit.loaders.PunishmentGUILoader;
import es.angelillo15.mast.bukkit.utils.FreezeUtils;
import es.angelillo15.mast.bukkit.utils.Logger;
import es.angelillo15.mast.bukkit.utils.Metrics;
import es.angelillo15.mast.api.utils.PermsUtils;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import es.angelillo15.mast.papi.MAStaffExtension;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.SneakyThrows;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import es.angelillo15.mast.api.database.PluginConnection;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class MAStaff extends JavaPlugin implements MAStaffInstance<JavaPlugin> {
    @Getter
    static final int version = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);
    @Getter
    private static MAStaff plugin;
    @Getter
    private static boolean glowEnabled = false;
    private boolean debug = false;
    private static ILogger logger;
    @Getter
    private static PluginConnection pluginConnection;
    @Getter
    private static Connection connection;
    @Getter
    private static int currentVersion;
    @Getter
    private static int spiVersion;
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
    }

    @Override
    public void registerCommands() {
        getCommand("staff").setExecutor(new StaffCMD());
        if (Config.Freeze.enabled()) getCommand("freeze").setExecutor(new FreezeCMD());
        getCommand("mast").setExecutor(new MAStaffCMD());
        getCommand("staffchat").setExecutor(new StaffChatCMD());
    }

    @Override
    public void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(injector.getInstance(OnJoin.class), this);
        pm.registerEvents(injector.getInstance(OnItemClick.class), this);
        pm.registerEvents(injector.getInstance(OnItemDrop.class), this);
        pm.registerEvents(injector.getInstance(OnInventoryClick.class), this);
        pm.registerEvents(injector.getInstance(OnItemClickInteract.class), this);
        pm.registerEvents(injector.getInstance(OnJoinLeave.class), this);
        pm.registerEvents(injector.getInstance(OnItemDrop.class), this);
        if (Config.Freeze.enabled()) pm.registerEvents(injector.getInstance(FreezeListener.class), this);
        pm.registerEvents(injector.getInstance(OnItemGet.class), this);
        pm.registerEvents(injector.getInstance(OnPlayerInteractAtEntityEvent.class), this);
        pm.registerEvents(injector.getInstance(OnAttack.class), this);
        if (Config.silentOpenChest()) pm.registerEvents(injector.getInstance(OnOpenChest.class), this);
        if (version >= 19) pm.registerEvents(injector.getInstance(OnBlockReceiveGameEvent.class), this);
        if (version >= 9) pm.registerEvents(injector.getInstance(OnSwapHand.class), this);
        if (version >= 9) pm.registerEvents(injector.getInstance(OnAchievement.class), this);
        FreezeUtils.setupMessageSender();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void loadDatabase() {
        YamlFile config = ConfigLoader.getConfig().getConfig();
        DataProvider dataProvider = DataProvider.valueOf(config
                .getString("Database.type").toUpperCase()
        );
        try {
            switch (dataProvider) {
                case MYSQL:
                    pluginConnection = new PluginConnection(
                            config.getString("Database.host"),
                            config.getInt("Database.port"),
                            config.getString("Database.database"),
                            config.getString("Database.user"),
                            config.getString("Database.password")
                    );
                    break;
                case SQLITE:
                    pluginConnection = new PluginConnection(
                            getPlugin().getDataFolder().getAbsolutePath()
                    );
                    break;
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
                e1.printStackTrace();
            }
        }
        PluginConnection.getQueries().createTables();

        logger.info(TextUtils.colorize("&aConnected to the {type} database successfully.")
                .replace("{type}", PluginConnection.getDataProvider().name())
        );
    }

    @SneakyThrows
    @Override
    public void loadModules() {
        ItemsLoader.load();
        CustomItemsLoader.load();
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
        getCommand("staff").setExecutor(null);
        getCommand("freeze").setExecutor(null);
        getCommand("mast").setExecutor(null);
        getCommand("staffchat").setExecutor(null);
    }

    @Override
    public void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void unloadDatabase() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        ItemsLoader.load();
        CustomItemsLoader.load();
        logger.debug("Loading punishments GUI...");
        PunishmentGUILoader.load();
        logger.debug("Registering Commands...");
        registerCommands();
        logger.debug("Registering Listeners...");
        registerListeners();
        logger.debug("Loading minimessage");
        setupMiniMessage();
        logger.debug("reloading addons...");
        AddonsLoader.reload();
        logger.debug("Checking for updates...");
        debugInfo();
        logger.debug("Starting tasks...");
        AsyncThreadKt.start();
        new Thread(this::checkUpdates);
        long end = System.currentTimeMillis();
        logger.debug("Reloaded successfully in {time}ms ✔️"
                .replace("{time}", String.valueOf(end - start))
        );
    }

    public void debugInfo() {
        logger.debug("Debug info:");
        logger.debug("Server version: 1." + version);
        logger.debug("Plugin version: " + getDescription().getVersion());
        logger.debug("Glow enabled: " + glowEnabled);
        logger.debug("Plugin connection: " + PluginConnection.getDataProvider().name());
    }

    public void checkUpdates() {

        HttpResponse<String> response = Unirest.get("https://api.spigotmc.org/legacy/update.php?resource=105713")
                .asString();


        currentVersion = Integer.parseInt(getDescription().getVersion()
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

    public void inject() {
        getPLogger().debug("Injecting...");
        injector = Guice.createInjector(new BukkitInjector());

        StaticMembersInjector.injectStatics(injector, LegacyStaffPlayersManagers.class);
        StaticMembersInjector.injectStatics(injector, LegacyUserDataManager.class);
    }

    public void registerPlaceholderAPI() {
        if (!MAStaffInstance.placeholderCheck()) return;

        injector.getInstance(MAStaffExtension.class).register();
    }

    @Override
    public IServerUtils getServerUtils() {
        return null;
    }

    @Override
    public IStaffPlayer createStaffPlayer(Player player) {
        return injector.getInstance(StaffPlayer.class).setPlayer(player);
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
}
