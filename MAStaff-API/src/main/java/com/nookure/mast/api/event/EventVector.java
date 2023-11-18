package com.nookure.mast.api.event;

import java.lang.reflect.Method;

public class EventVector {
  private final Method method;
  private final Object listener;
  private final MastSubscribe mastSubscribe;

  public EventVector(Method method, Object listener, MastSubscribe mastSubscribe) {
    this.method = method;
    this.listener = listener;
    this.mastSubscribe = mastSubscribe;
  }

  public Method getMethod() {
    return method;
  }

  public Object getListener() {
    return listener;
  }

  public MastSubscribe getMastSubscribe() {
    return mastSubscribe;
  }

  @Override
  public String toString() {
    return "EventVector{" +
        "method=" + method +
        ", listener=" + listener +
        ", mastSubscribe=" + mastSubscribe +
        '}';
  }
}
