package es.angelillo15.mast.api.redis;

public class RedisEvent extends Event {

    private final String message;
    private final String serverName;

    public RedisEvent(String message, String serverName) {
        this.message = message;
        this.serverName = serverName;
    }

    /**
     * Get the message of redis
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the server name of the instance that triggered the event
     * @return The server name
     */
    public String getServerName() {
        return serverName;
    }
}
