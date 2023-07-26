package es.angelillo15.mast.api.redis.events.staff;

import es.angelillo15.mast.api.event.Events;
import es.angelillo15.mast.api.redis.RedisEvent;

public class StaffSwitchServerEvent extends RedisEvent {
    private String staffName;
    private String serverFrom;
    private String serverTo;

    public StaffSwitchServerEvent(String message, String serverName) {
        super(message, serverName);
        String[] split = message.split(">");
        this.staffName = split[1];
        this.serverFrom = split[2];
        this.serverTo = split[3];
    }

    public StaffSwitchServerEvent(String serverName, String staffName, String serverFrom, String serverTo) {
        super(Events.STAFF_SWITCH_SERVER.getEventName() + ">" + staffName + ">" + serverFrom + ">" + serverTo, serverName);
    }

    /**
     * Get the staff name from the event
     * @return The staff name
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Get the server from the event
     * @return The server from
     */
    public String getServerFrom() {
        return serverFrom;
    }

    /**
     * Get the server to the event
     * @return The server to
     */
    public String getServerTo() {
        return serverTo;
    }
}
