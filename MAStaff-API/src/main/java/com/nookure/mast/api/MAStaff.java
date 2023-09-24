package com.nookure.mast.api;

import com.google.inject.Injector;
import com.nookure.mast.api.addons.annotations.Addon;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.IStaffPlayer;
import com.nookure.mast.api.cmd.Command;
import es.angelillo15.mast.api.nms.VersionSupport;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStream;

public interface MAStaff {
  /**
   * Gets the plugin logger
   *
   * @return The plugin logger
   */
  ILogger getPLogger();

  /**
   * Registers a command
   *
   * @param command The command to register
   * @see Command
   */
  void registerCommand(Command command);

  /**
   * Unregister a command
   *
   * @param command The command to unregister
   * @see Command
   */
  void unregisterCommand(Command command);

  /**
   * Checks if the plugin is in debug mode
   *
   * @return True if the plugin is in debug mode
   */
  boolean isDebug();

  /**
   * Sets the plugin debug mode
   *
   * @param debug True to enable debug mode
   */
  void setDebug(boolean debug);

  /**
   * Gets the server utils
   *
   * @return The server utils
   * @see IServerUtils
   */
  IServerUtils getServerUtils();

  /**
   * Gets the plugin data folder
   *
   * @return The plugin data folder
   */
  File getPluginDataFolder();

  /**
   * Gets a plugin resource
   *
   * @param s The resource name
   * @return The resource as an InputStream
   * @see InputStream
   */
  InputStream getPluginResource(String s);

  /**
   * Gets the plugin injector
   *
   * @return The plugin injector
   */
  Injector getInjector();


  /**
   * Register plugin listeners
   */
  void registerListeners();

  /**
   * Registers the plugin commands
   */
  void registerCommands();

  /**
   * Loads the plugin config
   */
  void loadConfig();

  /**
   * Draws the plugin logo
   */
  void drawLogo();

  /**
   * Loads the plugin database
   */
  void loadDatabase();

  /**
   * Loads the plugin modules
   */
  void loadModules();

  /**
   * Unregisters the plugin listeners
   */
  void unregisterCommands();

  /**
   * Unregister the plugin listeners
   */
  void unregisterListeners();

  /**
   * Unloads the plugin database
   */
  void unloadDatabase();

  /**
   * Reloads the plugin
   */
  void reload();

  /**
   * Creates a staff player
   * Only works on Bukkit
   *
   * @param player The player
   * @return The staff player
   * @see IStaffPlayer
   */
  default IStaffPlayer createStaffPlayer(Player player) {
    return null;
  }

  /**
   * Gets the plugin version support for NMS
   *
   * @return The plugin version support
   * @see VersionSupport
   */
  default VersionSupport getVersionSupport() {
    return null;
  }

  /**
   * Gets the current supported platform
   *
   * @return The current supported platform
   */
  Addon.AddonPlatform getPlatform();
}
