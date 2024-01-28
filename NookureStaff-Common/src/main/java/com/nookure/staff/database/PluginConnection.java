package com.nookure.staff.database;

import com.craftmend.storm.Storm;
import com.craftmend.storm.StormOptions;
import com.craftmend.storm.connection.hikaricp.HikariDriver;
import com.craftmend.storm.logger.StormLogger;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.config.partials.DatabaseConfig;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.database.DataProvider;
import com.nookure.staff.api.model.StaffDataModel;
import com.nookure.staff.database.driver.SqliteFileDriver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

@Singleton
public class PluginConnection extends AbstractPluginConnection {
  @Inject
  private Logger logger;
  @Inject
  private NookureStaff plugin;
  private Storm storm;
  private Connection connection;
  private HikariDataSource hikariDataSource;

  @Override
  public void connect(@NotNull DatabaseConfig config) {
    Objects.requireNonNull(config, "config is null");

    logger.debug("Connecting to the database");
    logger.debug("Debug database data: " + config);

    if (connection != null) {
      return;
    }

    if (config.getType() == DataProvider.MYSQL) {
      loadMySQL(config);
      return;
    }

    loadSqlite();

    try {
      storm.registerModel(new StaffDataModel());
      storm.runMigrations();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    logger.info("<green>Successfully connected to the database!");
  }

  private void loadMySQL(@NotNull DatabaseConfig config) {
    Objects.requireNonNull(config, "config is null");

    HikariConfig hikariConfig = getHikariConfig(config);

    try {
      hikariDataSource = new HikariDataSource(hikariConfig);
      connection = hikariDataSource.getConnection();
      storm = new Storm(getStormOptions(logger), new HikariDriver(hikariDataSource));
    } catch (Exception e) {
      logger.severe("An error occurred while connecting to the database");
      logger.severe("Now trying to connect to SQLite");
      loadSqlite();
    }
  }

  @NotNull
  private static HikariConfig getHikariConfig(@NotNull DatabaseConfig config) {
    Objects.requireNonNull(config, "config is null");

    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(
        "jdbc:mysql://"
            + config.getHost()
            + ":"
            + config.getPort()
            + "/"
            + config.getDatabase()
            + "?autoReconnect=true&useUnicode=yes");
    hikariConfig.setUsername(config.getUsername());
    hikariConfig.setPassword(config.getPassword());
    hikariConfig.setMaximumPoolSize(20);
    hikariConfig.setConnectionTimeout(30000);
    hikariConfig.setLeakDetectionThreshold(0);
    return hikariConfig;
  }

  private void loadSqlite() {
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      logger.severe("<red>┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
      logger.severe("<red>┃ The SQLite driver couldn't be found, contact nookure support             ┃");
      logger.severe("<red>┃ to get help with this issue.                                             ┃");
      logger.severe("<red>┃                                                                          ┃");
      logger.severe("<red>┃ This is a fatal error, the plugin will now disable itself.               ┃");
      logger.severe("<red>┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

      throw new RuntimeException(e);
    }

    try {
      String url = "jdbc:sqlite:" + plugin.getPluginDataFolder() + "/database.db";
      connection = DriverManager.getConnection(url);
      storm = new Storm(getStormOptions(logger), new SqliteFileDriver(connection));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private static StormOptions getStormOptions(Logger logger) {
    StormOptions options = new StormOptions();

    options.setLogger(new StormLogger() {
      @Override
      public void warning(String s) {
        logger.warning(s);
      }

      @Override
      public void info(String s) {
        logger.info(s);
      }
    });

    return options;
  }

  @Override
  protected void close() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    if (hikariDataSource != null) {
      hikariDataSource.close();
    }
  }

  @Override
  public @NotNull Storm getStorm() {
    if (storm == null) {
      throw new IllegalStateException("The connection is not established");
    }

    return storm;
  }

  @Override
  public @NotNull Connection getConnection() {
    if (connection == null) {
      throw new IllegalStateException("The connection is not established");
    }

    return connection;
  }
}
