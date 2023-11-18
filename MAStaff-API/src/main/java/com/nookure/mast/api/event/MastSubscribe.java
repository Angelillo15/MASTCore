package com.nookure.mast.api.event;

import com.nookure.mast.api.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MastSubscribe {
  /**
   * The event priority
   * @return The event priority
   */
  EventPriority priority() default EventPriority.NORMAL;
}
