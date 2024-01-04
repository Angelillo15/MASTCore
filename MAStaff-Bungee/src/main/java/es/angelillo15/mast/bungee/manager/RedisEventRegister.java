package es.angelillo15.mast.bungee.manager;

import es.angelillo15.mast.api.event.Events;
import es.angelillo15.mast.api.redis.RedisEventManager;
import com.nookure.mast.bungee.MAStaff;

public class RedisEventRegister {
  public static void registerEvents() {
    RedisEventManager redisEventManager = RedisEventManager.getInstance();

    Events[] events = Events.values();

    for (Events event : events) {
      redisEventManager.registerEvent(event.getEventName(), event.getEvent());
    }

    MAStaff.getInstance().getPLogger().debug("Registered " + events.length + " events");
  }
}
