package es.angelillo15.mast.api.redis;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private EventManager() {
        // Singleton
    }

    private static EventManager instance = new EventManager();

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private Map<Class<? extends Event>, List<Method>> listeners = new HashMap<>();

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

                listeners.get(eventClass).add(method);
            }
        }
    }

    @SneakyThrows
    public void fireEvent(Event event) {
        List<Method> eventListeners = listeners.get(event.getClass());
        Listener listener = null;
        if (eventListeners != null) {
            for (Method method : eventListeners) {

                listener = (Listener) method.getDeclaringClass().getDeclaredConstructor().newInstance();

                try {
                    Class<?> eventClass = method.getParameterTypes()[0];
                    System.out.println(eventClass.cast(event).getClass().getSimpleName());
                    method.invoke(listener, eventClass.cast(event));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}