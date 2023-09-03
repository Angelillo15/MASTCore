package es.angelillo15.mast.api.event;

import java.lang.reflect.Method;

public class EventVector {
  private final Method method;
  private final Listener listener;

  public EventVector(Method method, Listener listener) {
    this.method = method;
    this.listener = listener;
  }

  public Method getMethod() {
    return method;
  }

  public Listener getListener() {
    return listener;
  }
}
