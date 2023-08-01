package es.angelillo15.mast.api.redis.events.staff.join;

import es.angelillo15.mast.api.event.Events;
import es.angelillo15.mast.api.redis.RedisEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffJoinEvent extends RedisEvent {
    private String staffName;
    public StaffJoinEvent(String message, String serverName) {
        super(message, serverName);
        this.staffName = message.split(">")[1];
    }

    public StaffJoinEvent(String serverName, ProxiedPlayer staff) {
        super(Events.STAFF_JOIN.getEventName() + ">" + staff.getName(), serverName);
    }

    /**
     * Get the name of the staff member who joined
     * @return The name of the staff member who joined
     */
    public String getStaffName() {
        return staffName;
    }
}
