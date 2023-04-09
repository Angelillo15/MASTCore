package es.angelillo15.mast.api.redis.events.server;

import es.angelillo15.mast.api.redis.RedisEvent;

public class ServerConnectedEvent extends RedisEvent {
    public ServerConnectedEvent(String message, String serverName) {
        super(message, serverName);
    }
}