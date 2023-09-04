package es.angelillo15.mast.api.event

import com.google.inject.Singleton
import java.lang.reflect.InvocationTargetException

@Singleton
@SuppressWarnings("UNCHECKED_CAST")
class EventManager {
  private val events: MutableMap<String, Class<out Event>> = HashMap()
  private val listeners: MutableMap<Class<out Event>, MutableList<EventVector>> = HashMap()

  /**
   * Register a listener
   * @param listener Listener to register
   */
  fun registerListener(listener: Listener) {
    val clazz: Class<*> = listener.javaClass
    for (method in clazz.declaredMethods) {
      if (method.isAnnotationPresent(EventHandler::class.java)) {
        val parameterTypes = method.parameterTypes
        if (parameterTypes.size != 1) {
          throw RuntimeException("Event handler method must have exactly one parameter")
        }
        val parameterType = parameterTypes[0]
        if (!Event::class.java.isAssignableFrom(parameterType)) {
          throw RuntimeException("Event handler method must have a parameter that extends Event")
        }

        val eventClass = parameterType as Class<out Event?>
        if (!listeners.containsKey(eventClass)) {
          listeners[eventClass] = ArrayList()
        }
        listeners[eventClass]!!.add(EventVector(method, listener))
      }
    }
  }

  /**
   * Register an event
   * @param name The event redis start name
   * @param event The event class
   */
  fun registerEvent(name: String, event: Class<out Event>) {
    events[name] = event
  }

  /**
   * Get an event class by name
   * @param name The event redis start name
   * @return The event class
   */
  fun getEvent(name: String): Class<out Event> {
    return events[name]!!
  }

  /**
   * Check if an event exists
   * @param name The event redis start name
   * @return If the event exists
   */
  fun eventExists(name: String): Boolean {
    return events.containsKey(name)
  }

  /**
   * Fire an event
   * @param event The event to fire
   */
  fun fireEvent(event: Event) {
    val eventListeners: List<EventVector>? = listeners[event.javaClass]
    var listener: Listener? = null
    if (eventListeners != null) {
      for (vector in eventListeners) {
        listener = vector.listener
        val method = vector.method
        try {
          val eventClass = method.parameterTypes[0]
          method.invoke(listener, eventClass.cast(event))
        } catch (e: IllegalAccessException) {
          throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
          throw RuntimeException(e)
        }
      }
    }
  }
}
