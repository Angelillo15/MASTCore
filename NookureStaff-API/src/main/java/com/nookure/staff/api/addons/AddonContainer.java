package com.nookure.staff.api.addons;

import com.nookure.staff.api.addons.annotations.Addon;

public interface AddonContainer {
  AddonDescription getDescription();

  Object getInstance();

  AddonStatus getStatus();

  void setStatus(AddonStatus status);

  void setInstance(Object instance);

  Addon getAddon();
}