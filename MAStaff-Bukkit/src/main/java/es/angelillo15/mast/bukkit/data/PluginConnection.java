package es.angelillo15.mast.bukkit.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import es.angelillo15.mast.api.database.DataProvider;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.utils.TextUtils;
import lombok.Getter;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PluginConnection {
    @Getter
    private static Connection connection;
    @Getter
    private static DataProvider dataProvider;
    private static HikariConfig config;
    private static HikariDataSource dataSource;

    public PluginConnection(String host, int port, String database, String user, String password){
        dataProvider = DataProvider.MYSQL;
        config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useUnicode=yes");
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(5000);
        config.setLeakDetectionThreshold(5000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dataSource = new HikariDataSource(config);

    }

    public PluginConnection(Path pluginPath){
        dataProvider = DataProvider.SQLITE;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            MAStaff.getPlugin().getPLogger().error((TextUtils.colorize("&c┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓")));
            MAStaff.getPlugin().getPLogger().error((TextUtils.colorize("&c┃ The SQLite driver couldn't be found!                                     ┃")));
            MAStaff.getPlugin().getPLogger().error((TextUtils.colorize("&c┃                                                                          ┃")));
            MAStaff.getPlugin().getPLogger().error((TextUtils.colorize("&c┃ Please, join our Discord server to get support:                          ┃")));
            MAStaff.getPlugin().getPLogger().error((TextUtils.colorize("&c┃ https://discord.nookure.com                                              ┃")));
            MAStaff.getPlugin().getPLogger().error((TextUtils.colorize("&c┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛")));
        }
        try {
            String dataPath = pluginPath + "/database.db";
            String url = "jdbc:sqlite:" + dataPath;
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            MAStaff.getPlugin().getPLogger().error("An error ocurred while trying to connect to the SQLite database: " + e.getMessage());
        }
    }
}