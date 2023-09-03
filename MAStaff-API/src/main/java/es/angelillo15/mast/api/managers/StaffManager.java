package es.angelillo15.mast.api.managers;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.utils.MAStaffInject;
import java.util.HashMap;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class StaffManager {
    @Inject
    private MAStaffInject instance;

    @Getter
    private HashMap<String, IStaffPlayer> staffPlayers = new HashMap<>();

    /**
     * @param staffPlayer The staff player to add
     */
    public void addStaffPlayer(IStaffPlayer staffPlayer) {
        staffPlayers.remove(staffPlayer.getPlayer().getName());

        staffPlayers.put(staffPlayer.getPlayer().getName(), staffPlayer);
    }

    /**
     * @param staffPlayer The staff player to remove
     */
    public void removeStaffPlayer(IStaffPlayer staffPlayer) {
        staffPlayers.remove(staffPlayer.getPlayer().getName());
    }

    /**
     * @param name The name of the staff player to remove
     */
    public void removeStaffPlayer(String name) {
        staffPlayers.remove(name);
    }

    /**
     * @param name The name of the staff player to get
     * @return The staff player
     */
    public IStaffPlayer getStaffPlayer(String name) {
        if (staffPlayers.get(name) == null)
            return instance.createStaffPlayer(Bukkit.getPlayer(name));

        return staffPlayers.get(name);
    }

    /**
     * @param player The name of the staff player to get
     * @return The staff player
     */
    public IStaffPlayer getStaffPlayer(Player player) {
        if (staffPlayers.get(player.getName()) == null)
            return instance.createStaffPlayer(player);

        return staffPlayers.get(player.getName());
    }

    /**
     * @param player The player to check if is in the map
     * @return true if the player is in the map or false if not
     */
    public boolean isStaffPlayer(Player player) {
        return staffPlayers.containsKey(player.getName());
    }

    /**
     * @param name The player to check if is in the map
     * @return true if the player is in the map or false if not
     */
    public boolean isStaffPlayer(String name) {
        return staffPlayers.containsKey(name);
    }

    /**
     * @param staffPlayer The staff player to check if is in the map
     * @return true if the player is in the map or false if not
     */
    public boolean isStaffPlayer(IStaffPlayer staffPlayer) {
        return staffPlayers.containsKey(staffPlayer.getPlayer().getName());
    }
}
