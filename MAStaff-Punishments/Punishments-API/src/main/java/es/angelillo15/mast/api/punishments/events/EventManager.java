package es.angelillo15.mast.api.punishments.events;

import com.nookure.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.models.BansTable;
import es.angelillo15.mast.api.utils.ServerUtils;

public abstract class EventManager {
    private static EventManager eventManager;

    public static EventManager getEventManager() {
        if (eventManager != null) {
            return eventManager;
        }

        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUNGEE) {
            eventManager = new BungeeEventManager();
        }

        return eventManager;
    }

    public abstract void sendPlayerBannedEvent(BansTable bansTable, CommandSender sender);

    public abstract void sendPlayerTryToJoinBannedEvent(BansTable banModel, String player);
}
