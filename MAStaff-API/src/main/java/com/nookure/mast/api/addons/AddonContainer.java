package com.nookure.mast.api.addons;

import com.nookure.mast.api.addons.annotations.Addon;

public interface AddonContainer {
  AddonDescription getDescription();

  Object getInstance();

  AddonStatus getStatus();

  void setStatus(AddonStatus status);

  Addon getAddon();
}
