package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.DataProvider;
import es.angelillo15.mast.bukkit.cmd.FreezeCMD;
import es.angelillo15.mast.bukkit.cmd.StaffChatCMD;
import es.angelillo15.mast.bukkit.cmd.mast.MAStaffCMD;
import es.angelillo15.mast.bukkit.cmd.staff.StaffCMD;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.listener.FreezeListener;
import es.angelillo15.mast.bukkit.listener.GlowJoin;
import es.angelillo15.mast.bukkit.listener.VanishListener;
import es.angelillo15.mast.bukkit.listener.clickListeners.OnItemClick;
import es.angelillo15.mast.bukkit.listener.OnJoin;
import es.angelillo15.mast.bukkit.listener.clickListeners.OnItemClickInteract;
import es.angelillo15.mast.bukkit.listener.staffmode.*;
import es.angelillo15.mast.bukkit.loaders.CustomItemsLoader;
import es.angelillo15.mast.bukkit.loaders.GlowLoader;
import es.angelillo15.mast.bukkit.loaders.ItemsLoader;
import es.angelillo15.mast.bukkit.utils.FreezeUtils;
import es.angelillo15.mast.bukkit.utils.Logger;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.bukkit.utils.PermsUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import es.angelillo15.mast.bukkit.data.PluginConnection;
import org.simpleyaml.configuration.file.YamlFile;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class MAStaff extends JavaPlugin implements MAStaffInstance {
    @Getter
    static final int version = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);
    @Getter
    private static MAStaff plugin;
    @Getter
    private static boolean glowEnabled = false;
    private boolean debug = true;
    private static ILogger logger;
    @Getter
    private static PluginConnection pluginConnection;
    @Getter
    private static Connection connection;

    public static String parseMessage(String messages) {
        return TextUtils.colorize(messages.replace("{prefix}", Messages.PREFIX())
        );
    }

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
        getCommand("staff").setExecutor(new StaffCMD());
        getCommand("freeze").setExecutor(new FreezeCMD());
        getCommand("mast").setExecutor(new MAStaffCMD());
        getCommand("staffchat").setExecutor(new StaffChatCMD());
    }

    public boolean placeholderCheck() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
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
        pm.registerEvents(new FreezeListener(), this);
        pm.registerEvents(new OnItemGet(), this);
        pm.registerEvents(new OnSwapHand(), this);
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
                            Path.of(getPlugin().getDataFolder().getAbsolutePath())
                    );
                    break;

            }

            connection = pluginConnection.getConnection();

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error((TextUtils.colorize("&c┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓")));
            logger.error((TextUtils.colorize("&c┃ An error ocurred while connecting to the MySQL database!                 ┃")));
            logger.error((TextUtils.colorize("&c┃ Please, check your database credentials.                                 ┃")));
            logger.error((TextUtils.colorize("&c┃ If you need help, join our Discord server to get support:                ┃")));
            logger.error((TextUtils.colorize("&c┃ https://discord.nookure.com                                              ┃")));
            logger.error((TextUtils.colorize("&c┃ The plugin is now connecting to a SQLite database...                     ┃")));
            logger.error((TextUtils.colorize("&c┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛")));
            try {
                pluginConnection = new PluginConnection(
                        Path.of(getPlugin().getDataFolder().getAbsolutePath())
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
        new InventoryAPI(this).init();

        if (version > 9) {
            if (this.getServer().getPluginManager().getPlugin("eGlow") != null && ConfigLoader.getGlow()
                    .getConfig().getBoolean("Config.enabled") &&
            this.getServer().getPluginManager().getPlugin("Vault") != null) {
                glowEnabled = true;
                getServer().getPluginManager().registerEvents(new GlowJoin(), this);
                GlowLoader.loadGlow();

                if (getServer().getPluginManager().getPlugin("Vault") == null) {
                    return;
                }

                PermsUtils.setupPermissions();

            } else {
                if(getServer().getPluginManager().getPlugin("Vault") == null) {
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
        logger.debug("Registering Commands...");
        registerCommands();
        logger.debug("Registering Listeners...");
        registerListeners();
        logger.debug("Reloaded successfully ✔️");
    }

    @Override
    public IStaffPlayer createStaffPlayer(Player player) {
        return new StaffPlayer(player);
    }
}
