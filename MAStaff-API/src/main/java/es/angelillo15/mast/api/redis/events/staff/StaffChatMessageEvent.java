package es.angelillo15.mast.api.redis.events.staff;

import es.angelillo15.mast.api.redis.Events;
import es.angelillo15.mast.api.redis.RedisEvent;

import java.util.Arrays;
import java.util.Base64;

public class StaffChatMessageEvent extends RedisEvent {
    private String staffName;
    private String message;
    private String server;

    public StaffChatMessageEvent(String message, String serverName) {
        super(message, serverName);
        String[] split = message.split(">&");
        this.staffName = split[1];
        this.server = split[2];

        if (split.length > 3) {
            this.message = split[3];
        } else {
            this.message = "";
        }
    }

    public StaffChatMessageEvent(String serverName, String staffName, String message, String server) {
        super(Events.STAFF_CHAT_MESSAGE.getEventName() + ">&" + staffName + ">&" + server + ">&" + message,  serverName);
    }

    /**
     * Get the staff name from the event
     * @return The staff name
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Get the message from the event
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the server from the event
     * @return The server
     */
    public String getServer() {
        return server;
    }
}
