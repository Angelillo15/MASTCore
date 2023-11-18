package com.nookure.mast.api.event.addons;

import com.nookure.mast.api.addons.AddonContainer;
import com.nookure.mast.api.event.Event;

public class AddonDisableEvent extends Event {
  private final AddonContainer container;

  public AddonDisableEvent(AddonContainer container) {
    this.container = container;
  }

  /**
   * Get the addon container
   *
   * @return addon container
   */
  public AddonContainer getContainer() {
    return container;
  }
}
