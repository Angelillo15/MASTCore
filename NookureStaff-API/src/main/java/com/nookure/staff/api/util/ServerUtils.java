package com.nookure.staff.api.util;

import com.nookure.staff.api.Permissions;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class ServerUtils {
  public static final boolean isPaper;

  static {
    isPaper = hasClass("com.destroystokyo.paper.PaperConfig") ||
        hasClass("io.papermc.paper.configuration.Configuration");
  }

  public static boolean hasClass(String className) {
    try {
      Class.forName(className);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  /**
   * Check if a player is online
   *
   * @param uuid The UUID of the player
   * @return true if the player is online, false otherwise
   */
  abstract public boolean isOnline(@NotNull UUID uuid);

  /**
   * Check if a player is online
   *
   * @param name The name of the player
   * @return true if the player is online, false otherwise
   */
  abstract public boolean isOnline(@NotNull String name);

  /**
   * Broadcast a message to the players with a specific permission
   *
   * @param message    The message to broadcast
   * @param permission The permission to check
   */
  abstract public void broadcast(@NotNull String message, @NotNull String permission);

  /**
   * Broadcast a message to the players with the permission {@link Permissions#STAFF_PERMISSION}
   *
   * @param message The message to broadcast
   */
  public void broadcastStaffMessage(@NotNull String message) {
    broadcast(message, Permissions.STAFF_PERMISSION);
  }
}
