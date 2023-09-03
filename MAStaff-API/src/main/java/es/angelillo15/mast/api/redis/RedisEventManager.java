package es.angelillo15.mast.api.redis;

import es.angelillo15.mast.api.event.Event;
import es.angelillo15.mast.api.event.EventHandler;
import es.angelillo15.mast.api.event.EventVector;
import es.angelillo15.mast.api.event.Listener;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisEventManager {
    private static RedisEventManager instance = new RedisEventManager();
    private Map<String, Class<? extends Event>> events = new HashMap<>();
    private Map<Class<? extends Event>, List<EventVector>> listeners = new HashMap<>();

    private RedisEventManager() {
        // Singleton
    }

    public static RedisEventManager getInstance() {
        if (instance == null) {
            instance = new RedisEventManager();
        }
        return instance;
    }


    /**
     * Register a listener
     * @param listener Listener to register
     */
    public void registerListener(Listener listener) {
        Class<?> clazz = listener.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length != 1) {
                    throw new RuntimeException("Event handler method must have exactly one parameter");
                }

                Class<?> parameterType = parameterTypes[0];

                if (!Event.class.isAssignableFrom(parameterType)) {
                    throw new RuntimeException("Event handler method must have a parameter that extends Event");
                }

                @SuppressWarnings("unchecked")
                Class<? extends Event> eventClass = (Class<? extends Event>) parameterType;

                if (!listeners.containsKey(eventClass)) {
                    listeners.put(eventClass, new ArrayList<>());
                }

                listeners.get(eventClass).add(new EventVector(method, listener));
            }
        }
    }

    /**
     * Register an event
     * @param name The event redis start name
     * @param event The event class
     */
    public void registerEvent(String name, Class<? extends Event> event) {
        events.put(name, event);
    }

    /**
     * Get an event class by name
     * @param name The event redis start name
     * @return The event class
     */
    public Class<? extends Event> getEvent(String name) {
        return events.get(name);
    }

    /**
     * Check if an event exists
     * @param name The event redis start name
     * @return If the event exists
     */
    public boolean eventExists(String name) {
        return events.containsKey(name);
    }

    /**
     * Fire an event
     * @param event The event to fire
     */
    @SneakyThrows
    public void fireEvent(Event event) {
        List<EventVector> eventListeners = listeners.get(event.getClass());
        Listener listener = null;
        if (eventListeners != null) {
            for (EventVector vector : eventListeners) {

                listener = vector.getListener();
                Method method = vector.getMethod();

                try {
                    Class<?> eventClass = method.getParameterTypes()[0];
                    method.invoke(listener, eventClass.cast(event));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}