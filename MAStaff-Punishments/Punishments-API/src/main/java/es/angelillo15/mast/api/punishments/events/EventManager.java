package es.angelillo15.mast.api.punishments.events;

import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.models.BanModel;
import es.angelillo15.mast.api.utils.ServerUtils;

public abstract class EventManager {
    private static EventManager eventManager;
    public abstract void sendPlayerBannedEvent(BanModel banModel, CommandSender sender);

    public abstract void sendPlayerTryToJoinBannedEvent(BanModel banModel, String player);

    public static EventManager getEventManager() {
        if (eventManager != null) {
            return eventManager;
        }

        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUNGEE) {
            eventManager = new BungeeEventManager();
        }

        return eventManager;
    }
}
