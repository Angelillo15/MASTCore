package es.angelillo15.mast.api.redis;

public enum Events {
    SERVER_CONNECTED("ServerConnectedEvent"),
    SERVER_DISCONNECTED("ServerDisconnectedEvent");

    private final String event;

    Events(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }
}
