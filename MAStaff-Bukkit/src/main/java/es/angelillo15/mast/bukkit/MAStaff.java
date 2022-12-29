package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.DataProvider;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.utils.Logger;
import es.angelillo15.mast.bukkit.utils.TextUtils;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import es.angelillo15.mast.bukkit.data.PluginConnection;
import org.simpleyaml.configuration.file.YamlFile;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class MAStaff extends JavaPlugin implements MAStaffInstance {
    @Getter
    private static MAStaff plugin;
    private boolean debug = false;
    private static ILogger logger;
    @Getter
    private static PluginConnection pluginConnection;
    @Getter
    private static Connection connection;

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

    }

    @Override
    public void registerListeners() {

    }

    @Override
    public void loadDatabase() {
        YamlFile config = ConfigLoader.getConfig().getConfig();
        DataProvider dataProvider = DataProvider.valueOf(config
                .getString("Database.type").toUpperCase()
        );
        try {
            switch (dataProvider){
                case MYSQL:
                    pluginConnection = new PluginConnection(
                            config.getString("Database.host"),
                            config.getInt("Database.port"),
                            config.getString("Database.database"),
                            config.getString("Database.username"),
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
        }

        logger.info(TextUtils.colorize("&aConnected to the {type} database successfully.")
                .replace("{type}", PluginConnection.getDataProvider().name())
        );

    }

    @Override
    public void loadModules() {

    }

    @Override
    public void unregisterCommands() {
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
        unloadDatabase();
        loadConfig();
        loadDatabase();
        unregisterCommands();
        unregisterListeners();
    }
}
