package es.angelillo15.mast.bukkit.listener;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.addons.AddonDisableEvent;
import com.nookure.mast.api.staff.StaffFeatureManager;

public class OnAddonDisable {
  @Inject
  private StaffFeatureManager manager;

  @MastSubscribe
  public void onAddonDisable(AddonDisableEvent event) {
    manager.unregisterAllFeatures(event.container());
  }
}
