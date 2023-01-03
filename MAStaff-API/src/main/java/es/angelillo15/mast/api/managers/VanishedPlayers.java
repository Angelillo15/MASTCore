package es.angelillo15.mast.api.managers;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishedPlayers {
    @Getter
    private static ArrayList<Player> vanishedPlayers = new ArrayList<>();

    /**
     * Add a player to the vanished players list
     * @param player the player to add
     */
    public static void addPlayer(Player player) {
        vanishedPlayers.add(player);
    }

    /**
     * Remove a player from the vanished players list
     * @param player the player to remove
     */
    public static void removePlayer(Player player) {
        vanishedPlayers.remove(player);
    }

    /**
     * Check if a player is vanished
     * @param player the player to check
     * @return true if the player is vanished or false if not
     */
    public static boolean isVanished(Player player) {
        return vanishedPlayers.contains(player);
    }

    /**
     * Clear vanished players list
     */
    public static void clear() {
        vanishedPlayers.clear();
    }

}
