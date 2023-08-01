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
     * Broadcast a message to the players with the permission "mast.staff"
     * @param message The message to broadcast
     */
    fun broadcastStaffMessage(message: String) {
        broadcastMessage(message, "mast.staff")
    }
}
