package es.angelillo15.mast.api.managers;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.exceptions.AlreadyInTheMapException;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class StaffPlayersManagers {
    private static HashMap<String, IStaffPlayer> staffPlayers = new HashMap<>();

    /**
     * @param staffPlayer The staff player to add
     */
    public static void addStaffPlayer(@NonNull IStaffPlayer staffPlayer) throws AlreadyInTheMapException {
        if(staffPlayers.containsKey(staffPlayer.getPlayer().getName())) throw new AlreadyInTheMapException("The player is already in the map");
        staffPlayers.put(staffPlayer.getPlayer().getName(), staffPlayer);
    }

    /**
     * @param staffPlayer The staff player to remove
     */
    public static void removeStaffPlayer(@NonNull IStaffPlayer staffPlayer) {
        staffPlayers.remove(staffPlayer.getPlayer().getName());
    }

    /**
     * @param name The name of the staff player to remove
     */
    public static void removeStaffPlayer(@NonNull String name) {
        staffPlayers.remove(name);
    }

    /**
     * @param player The player to get the staff player
     * @return The staff player
     */
    public static IStaffPlayer getStaffPlayer(@NonNull Player player) {
        return staffPlayers.get(player.getName());
    }

    /**
     * @param name The player to get the staff player
     * @return The staff player
     */
    public static IStaffPlayer getStaffPlayer(@NonNull String name) {
        return staffPlayers.get(name);
    }

    /**
     * @param player The player to check if is in the map
     * @return true if the player is in the map or false if not
     */
    public static boolean isStaffPlayer(@NonNull Player player) {
        return staffPlayers.containsKey(player.getName());
    }

    /**
     * @param name The player to check if is in the map
     * @return true if the player is in the map or false if not
     */
    public static boolean isStaffPlayer(@NonNull String name) {
        return staffPlayers.containsKey(name);
    }
}
