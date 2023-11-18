package com.nookure.mast.api.staff;

import com.nookure.mast.api.addons.AddonContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record StaffFeatureContainer(@NotNull StaffFeature feature, @Nullable String permission, @Nullable AddonContainer addon) {
  public StaffFeatureContainer(StaffFeature feature, String permission) {
    this(feature, permission, null);
  }

  public StaffFeatureContainer(StaffFeature feature) {
    this(feature, null, null);
  }

  /**
   * Check if the feature has a permission
   * @return true if the feature has a permission
   */
  public boolean hasPermission() {
    return permission != null;
  }

  /**
   * Check if the feature has an addon
   * @return true if the feature has an addon
   */
  public boolean hasAddon() {
    return addon != null;
  }
}
