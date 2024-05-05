package com.nookure.staff.api.database;

import com.craftmend.storm.Storm;
import com.nookure.staff.api.config.partials.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Objects;

/**
 * This class represents a connection to the database.
 * Use {@link #connect(DatabaseConfig)} to connect to the database.
 */
public abstract class AbstractPluginConnection {
  /**
   * Connects to the database.
   * <p>
   * This method should be called before any other method.
   * If the connection is already established, this method should do nothing.
   * If the connection is not established, this method should establish it.
   * If the connection is not established, and the connection fails, the plugin should
   * try using SQLite.
   * If the connection is not established, and the connection fails, and SQLite fails,
   * the plugin should disable itself.
   * </p>
   *
   * @param config the database config
   * @see DatabaseConfig
   */
  public abstract void connect(@NotNull DatabaseConfig config);

  /**
   * Closes the connection to the database.
   *
   * <p>
   * This method should be called when the plugin is disabled.
   * If the connection is already closed, this method should do nothing.
   * </p>
   */
  protected abstract void close();

  /**
   * Returns the Storm instance.
   * This should be called after {@link #connect(DatabaseConfig)}.
   * <p>
   * If the connection is not established, this method should throw an exception.
   * If the connection is established, this method should return the Storm instance.
   * </p>
   *
   * @return the Storm instance
   */
  public abstract @NotNull Storm getStorm();

  /**
   * Returns the connection to the database.
   * this should be called after {@link #connect(DatabaseConfig)}.
   * <p>
   * If the connection is not established, this method should throw an exception.
   * If the connection is established, this method should return the connection.
   * </p>
   *
   * @return the connection to the database
   */
  public abstract @NotNull Connection getConnection();

  /**
   * Returns the datasource to the database.
   * this should be called after {@link #connect(DatabaseConfig)}.
   * <p>
   * If the connection is not established, this method should throw an exception.
   * If the connection is established, this method should return the connection.
   * </p>
   *
   * @return the connection to the database
   */
  public abstract @NotNull HikariDataSource getHikariDataSource();

  /**
   * Returns the data provider.
   *
   * @return the data provider
   */
  public abstract @NotNull DataProvider getDataProvider();

  public abstract @NotNull DatabaseConfig getDatabaseConfig();

  /**
   * Creates a new HikariConfig object with the given database config.
   *
   * @param config the database config
   * @return a new HikariConfig object
   */
  @NotNull
  protected static HikariConfig getHikariConfig(@NotNull DatabaseConfig config) {
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
}
