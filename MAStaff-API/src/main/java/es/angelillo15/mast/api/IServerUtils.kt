package es.angelillo15.mast.api

import java.util.*

interface IServerUtils {
  /**
   * Check if a player is online
   * @param uuid The UUID of the player
   * @return true if the player is online, false otherwise
   */
  fun isOnline(uuid: UUID): Boolean

  /**
   * Check if a player is online
   * @param name The name of the player
   * @return true if the player is online, false otherwise
   */
  fun isOnline(name: String): Boolean

  /**
   * Get the IP of a player
   * @param uuid The UUID of the player
   * @return The IP of the player
   */
  fun getIP(uuid: UUID): String

  /**
   * Get the IP of a player
   * @param name The name of the player
   * @return The IP of the player
   */
  fun getIP(name: String): String

  /**
   * Get the UUID of a player
   * @param name The name of the player
   * @return The UUID of the player
   */
  fun getUUID(name: String): UUID

  /**
   * Get the name of a player
   * @param uuid The UUID of the player
   * @return The name of the player
   */
  fun getName(uuid: UUID): String

  /**
   * Broadcast a message to the players with a specific permission
   * @param message The message to broadcast
   * @param permission The permission to check
   */
  fun broadcastMessage(message: String, permission: String)

  /**
   * Kick a player
   * @param uuid The UUID of the player
   * @param reason The reason of the kick
   * @return true if the player was kicked, false otherwise
   */
  fun kickPlayer(uuid: UUID, reason: String): Boolean

  /**
   * Get the names of the online players
   * @return The names of the online players
   */
  fun getOnlinePlayersNames(): List<String>

  /**
   * Get the UUIDs of the online players
   * @return The UUIDs of the online players
   */
  fun getOnlinePlayersUUIDs(): List<UUID>

  /**
   * Execute a command
   * @param command The command to execute
   */
  fun executeCommand(command: String)

  /**
   * Kick a player
   * @param name The name of the player
   * @param reason The reason of the kick
   * @return true if the player was kicked, false otherwise
   */
  fun kickPlayer(name: String, reason: String): Boolean {
    return kickPlayer(getUUID(name), reason)
  }

  /**
   * Broadcast a message to the players with the permission "mast.staff"
   * @param message The message to broadcast
   */
  fun broadcastStaffMessage(message: String) {
    broadcastMessage(message, "mast.staff")
  }
}
