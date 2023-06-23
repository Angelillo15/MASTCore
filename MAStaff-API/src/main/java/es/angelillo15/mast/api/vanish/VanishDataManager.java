package es.angelillo15.mast.api.vanish;

import es.angelillo15.mast.api.IStaffPlayer;

import java.util.ArrayList;

public class VanishDataManager {
    private static final ArrayList<IStaffPlayer> vanishedPlayers = new ArrayList<>();

    /**
     * Returns the list of vanished players
     * @return ArrayList<IStaffPlayer>
     */
    public static ArrayList<IStaffPlayer> getVanishedPlayers() {
        return vanishedPlayers;
    }

    /**
     * Adds a player to the vanished players list
     * @param player
     */
    public static void addVanishedPlayer(IStaffPlayer player) {
        vanishedPlayers.add(player);
    }

    /**
     * Removes a player from the vanished players list
     * @param player
     */
    public static void removeVanishedPlayer(IStaffPlayer player) {
        vanishedPlayers.remove(player);
    }

    /**
     * Checks if a player is vanished
     * @param player
     * @return boolean
     */
    public static boolean isVanished(IStaffPlayer player) {
        return vanishedPlayers.contains(player);
    }

    /**
     * Checks if a player is vanished
     * @param player
     * @return boolean
     */
    public static boolean isVanished(String player) {
        for (IStaffPlayer staffPlayer : vanishedPlayers) {
            if (staffPlayer.getPlayer().getName().equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Clears the vanished players list
     */
    public static void clear() {
        vanishedPlayers.clear();
    }
}
