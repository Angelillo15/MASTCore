package com.nookure.staff.api.util;

/**
 * Simple abstract class to stream instances and load them.
 * This class should have access to the injector.
 *
 * @since 1.0.0
 */
public abstract class AbstractLoader {
  /**
   * load the instance.
   */
  public abstract void load();
}
