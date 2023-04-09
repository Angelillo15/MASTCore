package es.angelillo15.mast.bungee.manager;

import es.angelillo15.mast.api.managers.ManagerExecutor;
import es.angelillo15.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Config;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber implements ManagerExecutor {
    @Override
    public void load() {
        MAStaff.getInstance().getPLogger().info("Subscribing to channel: " + Config.Redis.getChannel());
        new Thread(() -> {
            RedisManager.getJedisSubscriber().subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    MAStaff.getInstance().getPLogger().info("Received message: " + message);
                }
            }, Config.Redis.getChannel());

        }).start();
    }
}
