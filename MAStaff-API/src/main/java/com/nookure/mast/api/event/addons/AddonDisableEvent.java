package com.nookure.mast.api.event.addons;

import com.nookure.mast.api.addons.AddonContainer;
import com.nookure.mast.api.event.Event;

public record AddonDisableEvent(AddonContainer container) implements Event {

  /**
   * Get the addon container
   *
   * @return addon container
   */
  @Override
  public AddonContainer container() {
    return container;
  }
}
