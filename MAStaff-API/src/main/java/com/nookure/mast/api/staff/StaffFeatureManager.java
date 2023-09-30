package com.nookure.mast.api.staff;

import com.nookure.mast.api.addons.AddonContainer;
import es.angelillo15.mast.api.IStaffPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface StaffFeatureManager {
  /**
   * Register a feature
   *
   * @param feature the feature to register
   */
  default void registerFeature(@NotNull StaffFeature feature) {
    registerFeature(feature, null, null);
  }

  /**
   * Register a feature
   *
   * @param feature    the feature to register
   * @param permission the permission to use the feature
   */
  default void registerFeature(@NotNull StaffFeature feature, @Nullable String permission) {
    registerFeature(feature, permission, null);
  }

  /**
   * Register a feature
   *
   * @param feature    the feature to register
   * @param permission the permission to use the feature
   * @param addon      the addon that registers the feature
   */
  void registerFeature(@NotNull StaffFeature feature, @Nullable String permission, @Nullable AddonContainer addon);

  /**
   * Unregister a feature
   *
   * @param feature the feature to unregister
   */
  void unregisterFeature(@NotNull StaffFeature feature);

  /**
   * Unregister a feature
   *
   * @param addon the addon that unregisters the feature
   */
  void unregisterAllFeatures(@NotNull AddonContainer addon);

  /**
   * Unregister all features
   */
  void unregisterAllFeatures();

  /**
   * Get the feature container
   *
   * @param feature the feature to get the container
   * @return the feature container
   */
  StaffFeatureContainer getFeatureData(@NotNull StaffFeature feature);

  /**
   * Get all the features
   *
   * @return the features
   */
  List<StaffFeatureContainer> getFeatures();

  /**
   * Get all the features of a player (with permissions)
   *
   * @param player the player to get the features
   * @return the features
   */
  default List<StaffFeatureContainer> getFeatures(@NotNull IStaffPlayer player) {
    return getFeatures().stream().filter(feature -> {
      assert feature.permission() != null;
      return player.getPlayer().hasPermission(feature.permission());
    }).toList();
  }
}
