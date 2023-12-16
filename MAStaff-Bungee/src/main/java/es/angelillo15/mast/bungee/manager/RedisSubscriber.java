package es.angelillo15.mast.bungee.manager;

import es.angelillo15.mast.api.event.Event;
import es.angelillo15.mast.api.managers.ManagerExecutor;
import es.angelillo15.mast.api.redis.RedisEventManager;
import com.nookure.mas.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Config;
import lombok.SneakyThrows;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber implements ManagerExecutor {
    @Override
    public void load() {
        MAStaff.getInstance().getPLogger().info("Subscribing to channel: " + Config.Redis.getChannel());
        new Thread(() -> {
            RedisManager.getJedisSubscriber().subscribe(new JedisPubSub() {
                @SneakyThrows
                @Override
                public void onMessage(String channel, String message) {
                    if (message.startsWith(Config.Redis.getServerName() + ":")) {
                        return;
                    }

                    MAStaff.getInstance().getPLogger().debug("Received message: " + message);

                    String messageWithoutServerName = message.split(":")[1].split(">")[0];

                    if (!RedisEventManager.getInstance().eventExists(messageWithoutServerName)) {
                        MAStaff.getInstance().getPLogger().debug("Event " + messageWithoutServerName + " doesn't exists");
                        return;
                    }

                    Event event = RedisEventManager.getInstance().getEvent(messageWithoutServerName)
                            .getDeclaredConstructor(String.class, String.class)
                            .newInstance(message.split(":")[1],
                                    message.split(":")[0]
                            );

                    RedisEventManager.getInstance().fireEvent(event);

                    MAStaff.getInstance().getPLogger().debug("Event " + messageWithoutServerName + " fired");
                }
            }, Config.Redis.getChannel());

        }).start();
    }
}
