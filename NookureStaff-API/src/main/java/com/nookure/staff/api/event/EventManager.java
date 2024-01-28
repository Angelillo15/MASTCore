package com.nookure.staff.api.event;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.exception.EventHandlerException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class EventManager {
  @Inject
  private NookureStaff instance;
  @Inject
  private Logger logger;
  private final Map<Class<? extends Event>, List<EventVector>> listeners = new ConcurrentHashMap<>();

  /**
   * Register a listener class
   * The class must have methods annotated with {@link NookSubscribe}
   * in order to work, otherwise it will be ignored
   *
   * @param clazz Listener class to register
   */
  private void registerListener(Class<?> clazz) {
    registerListener(instance.getInjector().getInstance(clazz));
  }

  /**
   * Unregister a listener class
   *
   * @param listener Listener class to unregister
   */
  public void unregisterListener(Object listener) {
    listeners.values().forEach(eventVectors ->
        eventVectors.removeIf(eventVector -> eventVector.listener().equals(listener))
    );
  }

  /**
   * Unregister all listeners
   */
  public void unregisterAllListeners() {
    listeners.clear();
  }

  /**
   * Register a listener object
   * The class must have methods annotated with {@link NookSubscribe}
   * in order to work, otherwise it will be ignored
   *
   * @param listener Listener object to register
   */
  public void registerListener(@NotNull Object listener) {
    Objects.requireNonNull(listener, "Listener cannot be null");

    Class<?> clazz = listener.getClass();

    for (Method method : clazz.getDeclaredMethods()) {
      if (!method.isAnnotationPresent(NookSubscribe.class)) {
        continue;
      }

      logger.debug("Registering event handler " + method.getName() + " in " + clazz.getName());

      NookSubscribe nookSubscribe = method.getAnnotation(NookSubscribe.class);

      Class<?>[] parameterTypes = method.getParameterTypes();

      if (parameterTypes.length != 1) {
        continue;
      }

      Class<?> parameterType = parameterTypes[0];

      if (!Event.class.isAssignableFrom(parameterType)) {
        throw new EventHandlerException("Event handler method must have a parameter that extends Event");
      }

      @SuppressWarnings({"unchecked"})
      Class<? extends Event> eventClass = (Class<? extends Event>) parameterType;

      if (!listeners.containsKey(eventClass)) {
        listeners.put(eventClass, new ArrayList<>());
      }

      listeners.get(eventClass).add(new EventVector(method, listener, nookSubscribe));
    }
  }

  /**
   * Call an event
   * This will call all the methods annotated with {@link NookSubscribe}
   * that are listening to the event
   *
   * @param event Event to call
   */
  public void fireEvent(@NotNull Event event) {
    Objects.requireNonNull(event, "Event cannot be null");

    List<EventVector> eventVectors = listeners.get(event.getClass());

    if (eventVectors == null) return;
    if (eventVectors.isEmpty()) return;

    eventVectors.sort((o1, o2) -> {
      NookSubscribe nookSubscribe1 = o1.nookSubscribe();
      NookSubscribe nookSubscribe2 = o2.nookSubscribe();

      return Integer.compare(nookSubscribe1.priority().getSlot(), nookSubscribe2.priority().getSlot());
    });

    eventVectors.forEach(eventVector -> {
      try {
        eventVector.method().invoke(eventVector.listener(), event);
      } catch (Exception e) {
        throw new EventHandlerException("Could not invoke event handler", e);
      }
    });
  }
}