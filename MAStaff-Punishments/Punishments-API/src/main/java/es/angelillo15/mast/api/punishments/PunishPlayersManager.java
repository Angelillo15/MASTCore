package es.angelillo15.mast.api.punishments;

import es.angelillo15.mast.api.MAStaffInstance;

import java.util.HashMap;

public class PunishPlayersManager {
    public static HashMap<String, IPunishPlayer> players = new HashMap<>();

    public static IPunishPlayer getPlayer(String uuid) {
        if (players.containsKey(uuid)) {
            return players.get(uuid);
        }
        return null;
    }

    public static void addPlayer(IPunishPlayer player) {
        players.put(player.getUUID(), player);
        MAStaffInstance.getLogger().debug("Added player " + player.getName() + " (" + player.getUUID() + ") to the cache");
    }

    public static void removePlayer(String uuid) {
        players.remove(uuid);
    }

    public static boolean isPlayer(String uuid) {
        return players.containsKey(uuid);
    }

    public static void clearCache() {
        players.clear();
    }

    public static HashMap<String, IPunishPlayer> getPlayers() {
        return players;
    }
}
