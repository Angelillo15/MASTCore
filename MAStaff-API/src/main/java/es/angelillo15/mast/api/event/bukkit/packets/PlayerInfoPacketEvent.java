package es.angelillo15.mast.api.event.bukkit.packets;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerInfoPacketEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final EnumWrappers.PlayerInfoAction playerInfoAction;
    @Getter
    private List<PlayerInfoData> infoDataList;
    @Getter
    private final Player player;

    private boolean isCancelled = false;

    public PlayerInfoPacketEvent(Player paramPlayer, List<PlayerInfoData> paramList, EnumWrappers.PlayerInfoAction paramPlayerInfoAction) {
        super(false);
        this.infoDataList = paramList;
        this.playerInfoAction = paramPlayerInfoAction;
        this.player = paramPlayer;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
