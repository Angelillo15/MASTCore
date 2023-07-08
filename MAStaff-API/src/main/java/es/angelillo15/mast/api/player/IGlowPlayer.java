package es.angelillo15.mast.api.player;

import es.angelillo15.mast.api.chat.api.ChatColor;

public interface IGlowPlayer {
    /**
     * Enable the glow effect
     */
    void enableGlow();

    /**
     * Disable the glow effect
     */
    void disableGlow();

    /**
     * @return the color of the glow
     */
    ChatColor getColor();
}
