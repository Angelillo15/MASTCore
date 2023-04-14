package es.angelillo15.mast.bungee.manager;

import es.angelillo15.mast.api.redis.EventManager;
import es.angelillo15.mast.api.redis.Events;
import es.angelillo15.mast.bungee.MAStaff;

public class RedisEventRegister {
    public static void registerEvents() {
        EventManager eventManager = EventManager.getInstance();

        Events[] events = Events.values();

        for (Events event : events) {
            eventManager.registerEvent(event.getEventName(), event.getEvent());
        }

        MAStaff.getInstance().getPLogger().debug("Registered " + events.length + " events");
    }
}
