package com.nookure.staff.api.extension;

import com.nookure.staff.api.StaffPlayerWrapper;

public abstract class VanishExtension extends StaffPlayerExtension {
  public VanishExtension(StaffPlayerWrapper player) {
    super(player);
  }

  public abstract void enableVanish(boolean silent);

  public abstract void disableVanish(boolean silent);

  public abstract boolean isVanished();

  public abstract void setVanished(boolean vanished);
}
