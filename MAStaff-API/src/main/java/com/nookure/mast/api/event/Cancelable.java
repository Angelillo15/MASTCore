package com.nookure.mast.api.event;

public interface Cancelable {
  /**
   * Check if the event is cancelled
   *
   * @return true if cancelled
   */
  boolean isCancelled();

  /**
   * Set the event cancelled
   *
   * @param cancelled true if cancelled
   */
  void setCancelled(boolean cancelled);
}
