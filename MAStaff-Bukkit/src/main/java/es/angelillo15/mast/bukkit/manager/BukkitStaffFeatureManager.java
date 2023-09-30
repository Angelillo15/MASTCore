package es.angelillo15.mast.bukkit.manager;

import com.nookure.mast.api.addons.AddonContainer;
import com.nookure.mast.api.staff.StaffFeature;
import com.nookure.mast.api.staff.StaffFeatureContainer;
import com.nookure.mast.api.staff.StaffFeatureManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static kotlin.jvm.internal.Intrinsics.checkNotNull;

public class BukkitStaffFeatureManager implements StaffFeatureManager {
  private final Map<StaffFeature, StaffFeatureContainer> features = new ConcurrentHashMap<>();

  @Override
  public void registerFeature(@NotNull StaffFeature feature, @Nullable String permission, @Nullable AddonContainer addon) {
    checkNotNull(feature, "StaffFeature cannot be null");
    features.put(feature, new StaffFeatureContainer(feature, permission, addon));
  }

  @Override
  public void unregisterFeature(@NotNull StaffFeature feature) {
    checkNotNull(feature, "StaffFeature cannot be null");
    features.remove(feature);
  }

  @Override
  public void unregisterAllFeatures(@NotNull AddonContainer addon) {
    checkNotNull(addon, "AddonContainer cannot be null");
    features.values().removeIf(staffFeatureData -> {
      assert staffFeatureData.addon() != null;
      return staffFeatureData.addon().equals(addon);
    });
  }

  @Override
  public void unregisterAllFeatures() {
    features.clear();
  }

  @Override
  public StaffFeatureContainer getFeatureData(@NotNull StaffFeature feature) {
    checkNotNull(feature, "StaffFeature cannot be null");
    return features.get(feature);
  }

  @Override
  public List<StaffFeatureContainer> getFeatures() {
    return features.values().stream().toList();
  }
}
