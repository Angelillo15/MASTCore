package es.angelillo15.mast.api.redis.events.staff;

import es.angelillo15.mast.api.redis.Events;
import es.angelillo15.mast.api.redis.RedisEvent;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StaffChatMessageEvent extends RedisEvent {
    private String staffName;
    private String staffMessage;
    private String server;

    public StaffChatMessageEvent(String message, String serverName) {
        super(message, serverName);
        String[] split = message.split(">");
        this.staffName = split[1];
        this.server = split[2];

        if (split.length > 3) {
            this.staffMessage = new String(Base64.getDecoder().decode(split[3]), StandardCharsets.UTF_8);
        } else {
            this.staffMessage = "";
        }
    }

    public StaffChatMessageEvent(String serverName, String staffName, String message, String server) {
        // Encode to base64
        super(Events.STAFF_CHAT_MESSAGE.getEventName() + ">" + staffName + ">" + server + ">" +
                Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8)),
                serverName
        );

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
    public String getStaffMessage() {
        return staffMessage;
    }

    /**
     * Get the server from the event
     * @return The server
     */
    public String getServer() {
        return server;
    }
}
