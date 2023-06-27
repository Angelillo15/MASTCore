package es.angelillo15.mast.api.player;

import org.bukkit.entity.Player;

public interface IVanishPlayer {
    /**
     * Enable vanish for the player
     */
    void enableVanish();

    /**
     * Disable vanish for the player
     */
    void disableVanish();

    /**
     * Check if the player is vanished
     * @return true if the player is vanished, false otherwise
     */
    boolean isVanished();

    /**
     * Check if the player is vanished for a specific player
     * @param player the player to check
     * @return true if the player is vanished for the specific player, false otherwise
     */
    boolean isVanishedFor(Player player);

    /**
     * Send the player info change game mode packet to all the players that can see the player
     * @param vanished
     */
    public void sendPlayerInfoChangeGameModePacket(boolean vanished);
}
