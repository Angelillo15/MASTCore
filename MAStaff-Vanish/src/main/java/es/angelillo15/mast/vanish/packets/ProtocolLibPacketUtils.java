package es.angelillo15.mast.vanish.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import es.angelillo15.mast.api.MAStaffInstance;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ProtocolLibPacketUtils implements IPacketUtils {
    @Override
    public void sendPlayerInfoChangeGameModePacket(Player player, Player staff, boolean vanished) {
        byte ping = 0;

        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packetContainer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE);

        ArrayList<PlayerInfoData> data = new ArrayList<>();

        GameMode gameMode = vanished ? GameMode.SPECTATOR : staff.getGameMode();

        PlayerInfoData playerInfoData = new PlayerInfoData(
                WrappedGameProfile.fromPlayer(staff),
                ping,
                EnumWrappers.NativeGameMode.fromBukkit(gameMode),
                WrappedChatComponent.fromText(staff.getPlayerListName())
        );

        data.add(playerInfoData);

        packetContainer.getPlayerInfoDataLists().write(0, data);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (Exception e) {
            MAStaffInstance.getLogger().error("Error sending PlayerInfo packet to " + player.getName() + " for " + staff.getName() + " (vanished: " + vanished + ")", e);
        }
    }
}
