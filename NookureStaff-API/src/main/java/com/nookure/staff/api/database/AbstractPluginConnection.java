package com.nookure.staff.api.database;

import com.craftmend.storm.Storm;
import com.nookure.staff.api.config.partials.DatabaseConfig;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

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
}
