package es.angelillo15.mast.api.event;

import es.angelillo15.mast.api.event.Event;
import es.angelillo15.mast.api.redis.events.server.ServerConnectedEvent;
import es.angelillo15.mast.api.redis.events.server.ServerDisconnectedEvent;
import es.angelillo15.mast.api.redis.events.staff.StaffChatMessageEvent;
import es.angelillo15.mast.api.redis.events.staff.StaffSwitchServerEvent;
import es.angelillo15.mast.api.redis.events.staff.join.StaffJoinEvent;
import es.angelillo15.mast.api.redis.events.staff.join.StaffLeaveEvent;

public enum Events {
    SERVER_CONNECTED("ServerConnectedEvent", ServerConnectedEvent.class),
    SERVER_DISCONNECTED("ServerDisconnectedEvent", ServerDisconnectedEvent.class),
    STAFF_SWITCH_SERVER("StaffSwitchServerEvent", StaffSwitchServerEvent.class),
    STAFF_CHAT_MESSAGE("StaffChatMessageEvent", StaffChatMessageEvent.class),
    STAFF_JOIN("StaffJoinEvent",StaffJoinEvent .class),
    STAFF_LEAVE("StaffLeaveEvent", StaffLeaveEvent.class);

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
