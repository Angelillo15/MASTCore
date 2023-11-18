package com.nookure.mast.api.event.addons;

import com.nookure.mast.api.addons.AddonContainer;
import com.nookure.mast.api.event.Event;

public class AddonEnableEvent extends Event {
  private final AddonContainer container;

  public AddonEnableEvent(AddonContainer container) {
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
