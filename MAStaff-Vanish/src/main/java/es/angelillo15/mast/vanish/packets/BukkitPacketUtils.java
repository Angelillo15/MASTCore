package es.angelillo15.mast.vanish.packets;

import org.bukkit.entity.Player;

public class BukkitPacketUtils implements IPacketUtils {
    @Override
    public void sendPlayerInfoChangeGameModePacket(Player player, Player staff, boolean vanished) {
        // Not implemented on Bukkit
    }
}
