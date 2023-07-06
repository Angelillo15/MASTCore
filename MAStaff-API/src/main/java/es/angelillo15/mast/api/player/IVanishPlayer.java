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

}
