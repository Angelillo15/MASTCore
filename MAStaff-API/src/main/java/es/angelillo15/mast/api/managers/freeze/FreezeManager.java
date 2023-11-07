package es.angelillo15.mast.api.managers.freeze;

import com.google.inject.Inject;
import com.nookure.mast.api.manager.FreezeVector;
import es.angelillo15.mast.api.IStaffPlayer;
import java.util.ArrayList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * This class is deprecated, use {@link com.nookure.mast.api.manager.FreezeManager} instead
 * @since 3.0.0
 */
@Deprecated
public class FreezeManager {
    @Inject
    private static com.nookure.mast.api.manager.FreezeManager freezeManager;

    /**
     * Freeze the player
     * @param staff IStaffPlayer who freeze the player
     * @param target Player to freeze
     */
    public static void freezePlayer(IStaffPlayer staff, Player target) {
        freezeManager.freezePlayer(staff, target, -1);
    }

    /**
     * Freeze the player
     * @param staff IStaffPlayer who freeze the player
     * @param target Player to freeze
     * @param time Time to freeze the player (in ms)
     */
    public static void freezePlayer(IStaffPlayer staff, Player target, long time) {
        freezeManager.freezePlayer(staff, target, time);
    }

    /**
     * Unfreeze the player
     * @param target Player
     */
    public static void unfreezePlayer(Player target) {
        freezeManager.unfreezePlayer(target);
    }

    /**
     * Unfreeze the player
     * @param target Player
     */
    public static void unfreezePlayer(String target) {
        freezeManager.unfreezePlayer(target);
    }

    /**
     * Check if the player is frozen
     * @param target Player
     * @return boolean
     */
    public static boolean isFrozen(Player target) {
        return freezeManager.isFrozen(target);
    }

    /**
     * Check if the player is frozen
     * @param target Player
     * @return boolean
     */
    public static boolean isFrozen(String target) {
        return freezeManager.isFrozen(target);
    }

    /**
     * Gets the frozen players list
     * @return ArrayList of OfflinePlayer
     */
    public static ArrayList<OfflinePlayer> getFrozenPlayers() {
        return freezeManager.getFrozenPlayers();
    }

    public static FreezeVector getFreezeVector(Player target) {
        return freezeManager.getFreezeVector(target);
    }
}
