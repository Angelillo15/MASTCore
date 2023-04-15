package es.angelillo15.mast.bungee.utils;

import java.util.HashMap;
import java.util.UUID;

public class PreviousServerManager {
    private static final HashMap<UUID, String> previousServer = new HashMap<>();

    /**
     * Set the previous server of the player
     * @param uuid Player UUID
     * @param server Server name
     */
    public static void setPreviousServer(UUID uuid, String server){
        previousServer.put(uuid, server);
    }

    /**
     * Get the previous server of the player
     * @param uuid Player UUID
     * @return String
     */
    public static String getPreviousServer(UUID uuid){
        return previousServer.get(uuid);
    }

    /**
     * Remove the previous server of the player
     * @param uuid Player UUID
     */
    public static void removePreviousServer(UUID uuid){
        previousServer.remove(uuid);
    }

    /**
     * Check if the player has a previous server
     * @param uuid Player UUID
     * @return boolean
     */
    public static boolean hasPreviousServer(UUID uuid){
        return previousServer.containsKey(uuid);
    }

    /**
     * Clear the previous server map
     */
    public static void clearPreviousServer(){
        previousServer.clear();
    }

    /**
     * Get the previous server map
     * @return HashMap<UUID, String>
     */
    public static HashMap<UUID, String> getPreviousServerMap(){
        return previousServer;
    }
}
