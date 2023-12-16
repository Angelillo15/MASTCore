package es.angelillo15.mast.bungee.manager;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.managers.ManagerExecutor;
import es.angelillo15.mast.api.redis.RedisEvent;
import com.nookure.mas.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Config;
import lombok.Getter;
import redis.clients.jedis.Jedis;

public class RedisManager implements ManagerExecutor {
    @Getter
    private static Jedis jedis;
    @Getter
    private static Jedis jedisSubscriber;

    /**
     * Send a message to the Redis server
     * @param message Message to send
     */
    public static void sendMessage(String message) {
        String messageToSend = Config.Redis.getServerName() + ":" + message;
        new Thread(() -> jedis.publish(Config.Redis.getChannel(), messageToSend)).start();
        MAStaff.getInstance().getPLogger().debug("Sent message: " + messageToSend);
    }

    /**
     * Send a message to the Redis server
     * @param message Message to send
     * @param args Arguments to send
     */
    public static void sendMessage(String message, String... args) {
        sendMessage(message + ":" + String.join(">", args));
    }

    /**
     * Send a RedisEvent to the Redis server
     * @param event Event to send
     */
    public static void sendEvent(RedisEvent event) {
        sendMessage(event.getMessage());
    }

    @Override
    public void load() {
        MAStaff.getInstance().getPLogger().debug("Loading RedisManager");
        if (!Config.Redis.isEnabled()) {
            MAStaff.getInstance().getPLogger().debug("Redis is disabled");
            return;
        }

        jedis = new Jedis(Config.Redis.getHost(),
                Config.Redis.getPort(),
                Config.Redis.getTimeout(),
                Config.Redis.getPoolSize());
        jedis.auth(Config.Redis.getPassword());
        jedis.select(Config.Redis.getDatabase());

        jedisSubscriber = new Jedis(Config.Redis.getHost(),
                Config.Redis.getPort(),
                Config.Redis.getTimeout(),
                Config.Redis.getPoolSize());
        jedisSubscriber.auth(Config.Redis.getPassword());
        jedisSubscriber.select(Config.Redis.getDatabase());

        MAStaff.getInstance().getPLogger().info(TextUtils.simpleColorize("&aConnected to Redis server"));
        RedisEventRegister.registerEvents();
        new RedisSubscriber().load();
    }


}
