package com.nookure.mast.tests.inject;

import com.nookure.mast.addon.ServerAddonManager;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.addons.AddonManager;
import es.angelillo15.mast.api.inject.CommonModule;

public class TestInjector extends CommonModule {
  private final MAStaff plugin;
  public TestInjector(MAStaff plugin) {
    super();
    this.plugin = plugin;
  }
  @Override
  protected void configure() {
    super.configure();
    bind(MAStaff.class).toInstance(plugin);
    bind(AddonManager.class).to(ServerAddonManager.class).asEagerSingleton();
  }
}
