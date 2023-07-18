package es.angelillo15.mast.api.redis.events.staff.join;

import es.angelillo15.mast.api.redis.Events;
import es.angelillo15.mast.api.redis.RedisEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffLeaveEvent extends RedisEvent {
    private String staffName;
    public StaffLeaveEvent(String message, String serverName) {
        super(message, serverName);
        this.staffName = message.split(">")[0];
    }

    public StaffLeaveEvent(String serverName, ProxiedPlayer staff) {
        super(Events.STAFF_LEAVE.getEventName() + ">" + staff.getName(), serverName);
    }

    /**
     * Get the name of the staff member who left
     * @return The name of the staff member who left
     */
    public String getStaffName() {
        return staffName;
    }
}
