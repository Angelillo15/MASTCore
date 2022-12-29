package es.angelillo15.mast.api.managers;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.exceptions.AlreadyInTheMapException;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class StaffPlayersManagers {
    private static HashMap<String, IStaffPlayer> staffPlayers = new HashMap<>();

    /**
     * @param staffPlayer The staff player to add
     */
    public static void addStaffPlayer(IStaffPlayer staffPlayer) throws AlreadyInTheMapException {
        if(staffPlayers.containsKey(staffPlayer.getPlayer().getName())) throw new AlreadyInTheMapException("The player is already in the map");
        staffPlayers.put(staffPlayer.getPlayer().getName(), staffPlayer);
    }

    /**
     * @param staffPlayer The staff player to remove
     */
    public static void removeStaffPlayer(IStaffPlayer staffPlayer) {
        staffPlayers.remove(staffPlayer.getPlayer().getName());
    }
}
