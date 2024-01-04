package com.nookure.mast.api.event;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.exceptions.EventHandlerException;
import es.angelillo15.mast.api.ILogger;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class EventManager {
  @Inject
  private MAStaff instance;
  @Inject
  private ILogger logger;
  private final Map<Class<? extends Event>, List<EventVector>> listeners = new ConcurrentHashMap<>();

  /**
   * Register a listener class
   * The class must have methods annotated with {@link MastSubscribe}
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
        eventVectors.removeIf(eventVector -> eventVector.getListener().equals(listener))
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
   * The class must have methods annotated with {@link MastSubscribe}
   * in order to work, otherwise it will be ignored
   *
   * @param listener Listener object to register
   */
  public void registerListener(@NotNull Object listener) {
    Intrinsics.checkNotNull(listener, "Listener cannot be null");

    Class<?> clazz = listener.getClass();

    for (Method method : clazz.getDeclaredMethods()) {
      if (!method.isAnnotationPresent(MastSubscribe.class)) {
        continue;
      }

      logger.debug("Registering event handler " + method.getName() + " in " + clazz.getName());

      MastSubscribe mastSubscribe = method.getAnnotation(MastSubscribe.class);

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

      listeners.get(eventClass).add(new EventVector(method, listener, mastSubscribe));
    }
  }

  /**
   * Call an event
   * This will call all the methods annotated with {@link MastSubscribe}
   * that are listening to the event
   *
   * @param event Event to call
   */
  public void fireEvent(@NotNull Event event) {
    Intrinsics.checkNotNull(event, "Event cannot be null");

    List<EventVector> eventVectors = listeners.get(event.getClass());

    if (eventVectors == null) return;
    if (eventVectors.isEmpty()) return;

    eventVectors.sort((o1, o2) -> {
      MastSubscribe mastSubscribe1 = o1.getMastSubscribe();
      MastSubscribe mastSubscribe2 = o2.getMastSubscribe();

      return Integer.compare(mastSubscribe1.priority().getSlot(), mastSubscribe2.priority().getSlot());
    });

    eventVectors.forEach(eventVector -> {
      try {
        eventVector.getMethod().invoke(eventVector.getListener(), event);
      } catch (Exception e) {
        throw new EventHandlerException("Could not invoke event handler", e);
      }
    });
  }
}
