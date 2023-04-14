package es.angelillo15.mast.bungee.listener.redis.server;

import es.angelillo15.mast.api.redis.EventHandler;
import es.angelillo15.mast.api.redis.Listener;
import es.angelillo15.mast.api.redis.events.server.ServerConnectedEvent;
import es.angelillo15.mast.api.redis.events.server.ServerDisconnectedEvent;

public class OnServer implements Listener {
    @EventHandler
    public void onServerConnectedEvent(ServerConnectedEvent event) {
        System.out.println("Server connected: " + event.getServerName());
    }

    @EventHandler
    public void onServerDisconnectedEvent(ServerDisconnectedEvent event) {
        System.out.println("Server disconnected: " + event.getServerName());
    }
}
