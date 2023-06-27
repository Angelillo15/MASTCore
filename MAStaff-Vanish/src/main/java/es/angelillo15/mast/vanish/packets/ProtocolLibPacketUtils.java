package es.angelillo15.mast.vanish.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.utils.ServerUtils;
import es.angelillo15.mast.vanish.MAStaffVanish;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ProtocolLibPacketUtils implements IPacketUtils {
    @Override
    public void sendPlayerInfoChangeGameModePacket(Player player, Player staff, boolean vanished) {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packetContainer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE);

        ArrayList<PlayerInfoData> data = new ArrayList<>();

        GameMode gameMode = vanished ? GameMode.SPECTATOR : staff.getGameMode();

        PlayerInfoData playerInfoData = getPlayerInfoData(player, gameMode);

        data.add(playerInfoData);

        packetContainer.getPlayerInfoDataLists().write(0, data);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (Exception e) {
            MAStaffInstance.getLogger().error("Error sending PlayerInfo packet to " + player.getName() + " for " + staff.getName() + " (vanished: " + vanished + ")", e);
        }
    }

    private PlayerInfoData getPlayerInfoData(Player player, GameMode gameMode) {
        PlayerInfoData playerInfoData = null;
        if (MAStaffInstance.version() >= 19) {
            playerInfoData = new PlayerInfoData(
                    WrappedGameProfile.fromPlayer(player),
                    player.getPing(),
                    EnumWrappers.NativeGameMode.fromBukkit(gameMode),
                    WrappedChatComponent.fromText(player.getPlayerListName()),
                    PlayerPublicKeyRetrieval.getPlayerPublicKey(player.getUniqueId())
            );
        } else {
            playerInfoData = new PlayerInfoData(
                    WrappedGameProfile.fromPlayer(player),
                    player.getPing(),
                    EnumWrappers.NativeGameMode.fromBukkit(gameMode),
                    WrappedChatComponent.fromText(player.getPlayerListName())
            );
        }

        return playerInfoData;
    }
}
