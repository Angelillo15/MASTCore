package es.angelillo15.mast.api.database;

import com.craftmend.storm.Storm;
import com.craftmend.storm.StormOptions;
import com.craftmend.storm.connection.sqlite.SqliteFileDriver;
import com.craftmend.storm.logger.StormLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.database.sql.CommonQueries;
import es.angelillo15.mast.api.database.sql.MySQLQueries;
import es.angelillo15.mast.api.database.sql.SQLiteQueries;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import lombok.SneakyThrows;

public class PluginConnection {
  @Getter private static Connection connection;
  @Getter private static DataProvider dataProvider;
  @Getter private static CommonQueries queries;
  private static HikariConfig config;
  @Getter private static Storm storm;
  private static HikariDataSource dataSource;
  @Getter private static PluginConnection instance;
  @Getter private Connection conn;

  @SneakyThrows
  public PluginConnection(String host, int port, String database, String user, String password) {
    dataProvider = DataProvider.MYSQL;
    config = new HikariConfig();
    config.setJdbcUrl(
        "jdbc:mysql://"
            + host
            + ":"
            + port
            + "/"
            + database
            + "?autoReconnect=true&useUnicode=yes");
    config.setUsername(user);
    config.setPassword(password);
    config.setMaximumPoolSize(20);
    config.setConnectionTimeout(30000);
    config.setLeakDetectionThreshold(0);
    dataSource = new HikariDataSource(config);

    storm = new Storm(getDefaultStormOptions(), new PluginDriver(dataSource));

    try {
      connection = dataSource.getConnection();
      conn = connection;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    CommonQueries.setConnection(connection);
    queries = new MySQLQueries();

    instance = this;
  }

  public PluginConnection(String pluginPath) {
    dataProvider = DataProvider.SQLITE;
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      MAStaffInstance.getLogger()
          .error(
              (TextUtils.simpleColorize(
                  "&c┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓")));
      MAStaffInstance.getLogger()
          .error(
              (TextUtils.simpleColorize(
                  "&c┃ The SQLite driver couldn't be found!                                     ┃")));
      MAStaffInstance.getLogger()
          .error(
              (TextUtils.simpleColorize(
                  "&c┃                                                                          ┃")));
      MAStaffInstance.getLogger()
          .error(
              (TextUtils.simpleColorize(
                  "&c┃ Please, join our Discord server to get support:                          ┃")));
      MAStaffInstance.getLogger()
          .error(
              (TextUtils.simpleColorize(
                  "&c┃ https://discord.nookure.com                                              ┃")));
      MAStaffInstance.getLogger()
          .error(
              (TextUtils.simpleColorize(
                  "&c┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛")));
    }

    try {
      String dataPath = pluginPath + "/database.db";
      String url = "jdbc:sqlite:" + dataPath;
      connection = DriverManager.getConnection(url);
      conn = connection;

      storm = new Storm(getDefaultStormOptions(), new SqliteFileDriver(new File(dataPath)));

      CommonQueries.setConnection(connection);
      queries = new SQLiteQueries();
    } catch (SQLException e) {
      MAStaffInstance.getLogger()
          .error(
              "An error ocurred while trying to connect to the SQLite database: " + e.getMessage());
    }

    instance = this;
  }

  public static StormOptions getDefaultStormOptions() {
    StormOptions options = new StormOptions();

    options.setLogger(
        new StormLogger() {
          @Override
          public void warning(String string) {
            MAStaffInstance.getLogger().warn(string);
          }

          @Override
          public void info(String string) {
            MAStaffInstance.getLogger().info(string);
          }
        });

    return options;
  }
}
