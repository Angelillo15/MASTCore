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
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.model.StaffDataModel;
import com.nookure.staff.database.driver.SqliteFileDriver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.ContainerConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.platform.mysql.MySqlPlatform;
import io.ebean.platform.sqlite.SQLitePlatform;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Singleton
public class PluginConnection extends AbstractPluginConnection {
  @Inject
  private Logger logger;
  @Inject
  private NookureStaff plugin;
  @Inject
  private MigrationService migrationService;
  private Storm storm;
  private ClassLoader classLoader;
  private Connection connection;
  private HikariDataSource hikariDataSource;
  private Database database;

  @Override
  public void connect(@NotNull DatabaseConfig config, ClassLoader classLoader) {
    requireNonNull(config, "config is null");

    this.classLoader = classLoader;

    logger.debug("Connecting to the database");
    logger.debug("Debug database data: " + config);

    if (connection != null) {
      return;
    }

    if (config.getType() == DataProvider.MYSQL)
      loadMySQL(config);
    else
      loadSqlite(config);

    try {
      storm.registerModel(new StaffDataModel());
      storm.runMigrations();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    logger.info("<green>Successfully connected to the database!");
  }

  private void loadMySQL(@NotNull DatabaseConfig config) {
    requireNonNull(config, "config is null");

    HikariConfig hikariConfig = getHikariConfig(config);

    try {
      Thread.currentThread().setContextClassLoader(classLoader);
      hikariDataSource = new HikariDataSource(hikariConfig);
      connection = hikariDataSource.getConnection();
      storm = new Storm(getStormOptions(logger), new HikariDriver(hikariDataSource));

      loadEbean(config);
      loadMigrations(config);
    } catch (Exception e) {
      logger.severe("An error occurred while connecting to the database");
      logger.severe("Now trying to connect to SQLite");
      logger.severe(e);
      loadSqlite(config);
    }
  }

  private void loadEbean(DatabaseConfig config) {
    io.ebean.config.DatabaseConfig ebeanConfig = getDatabaseConfig(config);
    database = DatabaseFactory.create(ebeanConfig);
  }

  @NotNull
  private io.ebean.config.DatabaseConfig getDatabaseConfig(DatabaseConfig config) {
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    dataSourceConfig.setUrl(hikariDataSource.getJdbcUrl());

    if (config.getType() == DataProvider.MYSQL) {
      dataSourceConfig.setUsername(hikariDataSource.getUsername());
      dataSourceConfig.setPassword(hikariDataSource.getPassword());
    }

    dataSourceConfig.setName("nkstaff");

    io.ebean.config.DatabaseConfig ebeanConfig = new io.ebean.config.DatabaseConfig();

    ebeanConfig.loadFromProperties();

    ContainerConfig containerConfig = new ContainerConfig();
    containerConfig.setActive(false);

    ebeanConfig.setDataSourceConfig(dataSourceConfig);

    ebeanConfig.setName("nkstaff");
    ebeanConfig.setClasses(List.of(PlayerModel.class));
    ebeanConfig.setDataSource(hikariDataSource);
    ebeanConfig.setDefaultServer(true);

    if (config.getType() == DataProvider.MYSQL) {
      ebeanConfig.setDatabasePlatform(new MySqlPlatform());
    } else {
      ebeanConfig.setDatabasePlatform(new SQLitePlatform());
    }

    return ebeanConfig;
  }

  private void loadMigrations(DatabaseConfig config) {
    migrationService.generateMigrations(new File(plugin.getPluginDataFolder(), "database"));
    File migrationsFolder = new File(plugin.getPluginDataFolder(), "database/dbmigration");
    logger.debug("Migrations folder: " + migrationsFolder.getAbsolutePath());

    migrationService.loadMigrations(config, hikariDataSource, migrationsFolder);
  }

  @NotNull
  private HikariConfig getHikariConfig(@NotNull DatabaseConfig config) {
    requireNonNull(config, "config is null");

    HikariConfig hikariConfig = new HikariConfig();
    if (config.getType() == DataProvider.MYSQL) {
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
    } else {
      hikariConfig.setJdbcUrl("jdbc:sqlite:" + plugin.getPluginDataFolder() + "/database.db");
    }

    hikariConfig.setMaximumPoolSize(20);
    hikariConfig.setConnectionTimeout(30000);
    hikariConfig.setLeakDetectionThreshold(0);
    return hikariConfig;
  }

  private void loadSqlite(DatabaseConfig config) {
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
      hikariDataSource = new HikariDataSource(getHikariConfig(config));

      loadEbean(config);
      loadMigrations(config);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void reload(@NotNull DatabaseConfig config, @NotNull ClassLoader classLoader) {
    close();
    connect(config, classLoader);
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
  public void close() {
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

    if (database != null) {
      database.shutdown(true, false);
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

  @Override
  public Database getEbeanDatabase() {
    if (connection == null) {
      throw new IllegalStateException("The connection is not established");
    }

    return database;
  }
}
