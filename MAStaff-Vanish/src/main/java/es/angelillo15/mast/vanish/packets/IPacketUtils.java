package es.angelillo15.mast.vanish.packets;

import org.bukkit.entity.Player;

public interface IPacketUtils {
    /**
     * Send a packet to change the appearance of the game mode of a player
     * @param player the player member that is going to see the change
     * @param staff the player that is in vanish
     * @param vanished true if the player is vanished, false otherwise
     */
    void sendPlayerInfoChangeGameModePacket(Player player, Player staff, boolean vanished);
}
