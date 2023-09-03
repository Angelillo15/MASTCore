package es.angelillo15.mast.api.managers.freeze;

import es.angelillo15.mast.api.IStaffPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FreezeManager {
    public static HashMap<String, FreezeVector> frozenPlayers = new HashMap<>();

    /**
     * Freeze the player
     * @param staff IStaffPlayer who freeze the player
     * @param target Player to freeze
     */
    public static void freezePlayer(IStaffPlayer staff, Player target) {
        frozenPlayers.put(target.getName(), new FreezeVector(staff, target));
    }

    /**
     * Unfreeze the player
     * @param target Player
     */
    public static void unfreezePlayer(Player target) {
        frozenPlayers.remove(target.getName());
    }

    /**
     * Unfreeze the player
     * @param target Player
     */
    public static void unfreezePlayer(String target) {
        frozenPlayers.remove(target);
    }

    /**
     * Check if the player is frozen
     * @param target Player
     * @return boolean
     */
    public static boolean isFrozen(Player target) {
        return frozenPlayers.containsKey(target.getName());
    }

    /**
     * Check if the player is frozen
     * @param target Player
     * @return boolean
     */
    public static boolean isFrozen(String target) {
        return frozenPlayers.containsKey(target);
    }

    /**
     * Gets the frozen players list
     * @return ArrayList of OfflinePlayer
     */
    public static ArrayList<OfflinePlayer> getFrozenPlayers() {
        ArrayList<OfflinePlayer> frozen = new ArrayList<>();
        frozenPlayers.forEach((name, vector) -> frozen.add(vector.getTarget()));
        return frozen;
    }

    public static FreezeVector getFreezeVector(Player target) {
        return frozenPlayers.get(target.getName());
    }
}
