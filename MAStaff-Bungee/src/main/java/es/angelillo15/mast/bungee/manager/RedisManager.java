package es.angelillo15.mast.bungee.manager;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.managers.ManagerExecutor;
import es.angelillo15.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Config;
import lombok.Getter;
import redis.clients.jedis.Jedis;

public class RedisManager implements ManagerExecutor {
    @Getter
    private static Jedis jedis;
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

        MAStaff.getInstance().getPLogger().info(TextUtils.simpleColorize("&aRedisManager loaded"));
    }
}
