package es.angelillo15.mast.api.managers;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FreezeManager {
    @Getter
    private static ArrayList<Player> frozenPlayers = new ArrayList<>();

    /**
     * Add a player to the frozen players list
     * @param player the player to add
     */
    public static void addPlayer(Player player) {
        frozenPlayers.add(player);
    }

    /**
     * Remove a player from the frozen players list
     * @param player the player to remove
     */
    public static void removePlayer(Player player) {
        frozenPlayers.remove(player);
    }

    /**
     * Check if a player is frozen
     * @param player the player to check
     * @return true if the player is frozen or false if not
     */
    public static boolean isFrozen(Player player) {
        return frozenPlayers.contains(player);
    }
}
