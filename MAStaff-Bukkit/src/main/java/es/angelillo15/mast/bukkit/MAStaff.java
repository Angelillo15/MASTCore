package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.*;
import es.angelillo15.mast.api.database.DataProvider;
import es.angelillo15.mast.api.utils.BukkitUtils;
import es.angelillo15.mast.bukkit.addons.AddonsLoader;
import es.angelillo15.mast.bukkit.cmd.FreezeCMD;
import es.angelillo15.mast.bukkit.cmd.StaffChatCMD;
import es.angelillo15.mast.bukkit.cmd.mast.MAStaffCMD;
import es.angelillo15.mast.bukkit.cmd.staff.StaffCMD;
import es.angelillo15.mast.bukkit.config.Config;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.legacy.BukkitLegacyLoader;
import es.angelillo15.mast.bukkit.listener.FreezeListener;
import es.angelillo15.mast.bukkit.listener.VanishListener;
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
import es.angelillo15.mast.bukkit.utils.PermsUtils;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import es.angelillo15.mast.api.database.PluginConnection;
import org.simpleyaml.configuration.file.YamlFile;

import java.sql.Connection;
import java.sql.SQLException;

public class MAStaff extends JavaPlugin implements MAStaffInstance<Plugin> {
    @Getter
    static final int version = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);
    @Getter
    private static MAStaff plugin;
    @Getter
    private static boolean glowEnabled = false;
    @Setter
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
    public static String parseMessage(String messages) {
        return TextUtils.colorize(messages.replace("{prefix}", Messages.PREFIX()));
    }

    @Override
    public void onEnable() {
        plugin = this;
        new Scheduler();
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
        new ConfigLoader().load();
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
        pm.registerEvents(new OnJoin(), this);
        pm.registerEvents(new OnItemClick(), this);
        pm.registerEvents(new OnItemDrop(), this);
        pm.registerEvents(new VanishListener(), this);
        pm.registerEvents(new OnInventoryClick(), this);
        pm.registerEvents(new OnItemClickInteract(), this);
        pm.registerEvents(new OnJoinLeave(), this);
        pm.registerEvents(new OnItemDrop(), this);
        if (Config.Freeze.enabled()) pm.registerEvents(new FreezeListener(), this);
        pm.registerEvents(new OnItemGet(), this);
        pm.registerEvents(new OnPlayerInteractAtEntityEvent(), this);
        pm.registerEvents(new OnAttack(), this);
        if (Config.silentOpenChest()) pm.registerEvents(new OnOpenChest(), this);
        if (version >= 19) pm.registerEvents(new OnBlockReceiveGameEvent(), this);
        if (version >= 9) pm.registerEvents(new OnSwapHand(), this);
        if (version >= 9) pm.registerEvents(new OnAchievement(), this);
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

            connection = pluginConnection.getConnection();

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error((TextUtils.colorize("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓")));
            logger.error((TextUtils.colorize("┃ An error ocurred while connecting to the MySQL database!                 ┃")));
            logger.error((TextUtils.colorize("┃ Please, check your database credentials.                                 ┃")));
            logger.error((TextUtils.colorize("┃ If you need help, join our Discord server to get support:                ┃")));
            logger.error((TextUtils.colorize("┃ https://discord.nookure.com                                              ┃")));
            logger.error((TextUtils.colorize("┃ The plugin is now connecting to a SQLite database...                     ┃")));
            logger.error((TextUtils.colorize("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛")));
            try {
                pluginConnection = new PluginConnection(
                        getPlugin().getDataFolder().getAbsolutePath()
                );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
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
            new BukkitLegacyLoader().load();
        }

        if (version > 9) {
            if (this.getServer().getPluginManager().getPlugin("eGlow") != null && ConfigLoader.getGlow()
                    .getConfig().getBoolean("Config.enabled") &&
                    this.getServer().getPluginManager().getPlugin("Vault") != null) {
                glowEnabled = true;
                GlowLoader.loadGlow();

                if (getServer().getPluginManager().getPlugin("Vault") == null) {
                    return;
                }

                PermsUtils.setupPermissions();

            } else {
                if (getServer().getPluginManager().getPlugin("Vault") == null) {
                    logger.warn(TextUtils.colorize("&cVault not found! Glow will not work!"));
                }

                if (this.getServer().getPluginManager().getPlugin("eGlow") == null) {
                    logger.warn(TextUtils.colorize("&ceGlow not found! Glow will not work!"));
                }

                if (!ConfigLoader.getGlow().getConfig().getBoolean("Config.enabled")) {
                    logger.warn(TextUtils.colorize("&cGlow is disabled! Glow will not work!"));
                }
            }
        }
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
        logger.debug("reloading addons...");
        AddonsLoader.reload();
        logger.debug("Checking for updates...");
        debugInfo();
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


        currentVersion = Integer.parseInt(getDescription().getVersion().replace(".", "")
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

    @Override
    public IServerUtils getServerUtils() {
        return null;
    }

    @Override
    public IStaffPlayer createStaffPlayer(Player player) {
        return new StaffPlayer(player);
    }

    @Override
    public Plugin getPluginInstance() {
        return this;
    }
}
