package es.angelillo15.mast.vanish.packets;

import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedProfilePublicKey;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.event.bukkit.packets.PlayerInfoPacketEvent;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerPublicKeyRetrieval implements Listener {
    @Getter
    private static final HashMap<UUID, WrappedProfilePublicKey.WrappedProfileKeyData> playerPublicKeys = new HashMap<>();

    @EventHandler
    public void onPacketSending(PlayerInfoPacketEvent paramPlayerInfoPacketEvent) {
        try {
            for (PlayerInfoData playerInfoData : paramPlayerInfoPacketEvent.getInfoDataList()) {
                playerPublicKeys.put(playerInfoData.getProfile().getUUID(), playerInfoData.getProfileKeyData());
                MAStaffInstance.getLogger().debug("Added player public key for " + playerInfoData.getProfile().getName() + " (" + playerInfoData.getProfile().getUUID() + ")");
            }
        } catch (Exception exception) {
            MAStaffInstance.getLogger().error("Error while handling PlayerInfoPacketEvent: " + exception.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent paramPlayerQuitEvent) {
        new Thread(() -> {
            playerPublicKeys.remove(paramPlayerQuitEvent.getPlayer().getUniqueId());
        }).start();
    }

    public static WrappedProfilePublicKey.WrappedProfileKeyData getPlayerPublicKey(UUID paramUUID) {
        return playerPublicKeys.get(paramUUID);
    }
}
