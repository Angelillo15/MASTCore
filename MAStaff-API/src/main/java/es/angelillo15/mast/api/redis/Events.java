package es.angelillo15.mast.api.redis;

import es.angelillo15.mast.api.redis.events.server.ServerConnectedEvent;
import es.angelillo15.mast.api.redis.events.server.ServerDisconnectedEvent;

public enum Events {
    SERVER_CONNECTED("ServerConnectedEvent", ServerConnectedEvent.class),
    SERVER_DISCONNECTED("ServerDisconnectedEvent", ServerDisconnectedEvent.class);

    private final String event;
    private final Class<? extends Event> eventClass;

    Events(String event, Class<? extends Event> eventClass) {
        this.event = event;
        this.eventClass = eventClass;
    }

    public String getEventName() {
        return event;
    }

    public Class<? extends Event> getEvent() {
        return eventClass;
    }
}
