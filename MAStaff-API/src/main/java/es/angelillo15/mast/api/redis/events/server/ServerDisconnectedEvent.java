package es.angelillo15.mast.api.redis.events.server;

import es.angelillo15.mast.api.redis.RedisEvent;

public class ServerDisconnectedEvent extends RedisEvent {
    public ServerDisconnectedEvent(String message, String serverName) {
        super(message, serverName);
    }
}
