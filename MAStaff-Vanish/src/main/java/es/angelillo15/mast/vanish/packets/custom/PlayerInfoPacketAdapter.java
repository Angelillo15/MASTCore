package es.angelillo15.mast.vanish.packets.custom;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.event.bukkit.packets.PlayerInfoPacketEvent;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerInfoPacketAdapter extends PacketAdapter {

    public PlayerInfoPacketAdapter() {
        super(MAStaffInstance.getInstance().getPluginInstance(),
                ListenerPriority.HIGH,
                Collections.singleton(PacketType.Play.Server.PLAYER_INFO),
                ListenerOptions.ASYNC
        );
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        try {
            event.setPacket(event.getPacket().shallowClone());

            ArrayList<PlayerInfoData> arrayList = new ArrayList<>(event.getPacket().getPlayerInfoDataLists().read(0));

            EnumWrappers.PlayerInfoAction playerInfoAction = event.getPacket().getPlayerInfoAction().read(0);
            PlayerInfoPacketEvent playerInfoPacketEvent = new PlayerInfoPacketEvent(event.getPlayer(), arrayList, playerInfoAction);

            this.plugin.getServer().getPluginManager().callEvent(playerInfoPacketEvent);

            if (playerInfoPacketEvent.getInfoDataList().isEmpty()) {
                event.setCancelled(true);
            }

            event.getPacket().getPlayerInfoDataLists().write(0, playerInfoPacketEvent.getInfoDataList());
        } catch (Exception exception) {
            MAStaffInstance.getLogger().error("Error while handling PlayerInfoPacketEvent: " + exception.getMessage());
        }
    }
}
