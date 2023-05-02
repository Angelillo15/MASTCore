package es.angelillo15.mast.api.punishments;

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
        players.put(player.getUUID().toString(), player);
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
}
