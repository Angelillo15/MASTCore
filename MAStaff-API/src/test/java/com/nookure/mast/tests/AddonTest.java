package com.nookure.mast.tests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nookure.mast.api.addons.AddonManager;
import com.nookure.mast.api.addons.AddonsUtils;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.tests.addon.TestEvent;
import com.nookure.mast.tests.inject.TestInjector;
import com.nookure.mast.tests.mocks.MAStaffMock;
import com.nookure.mast.tests.mocks.MockLogger;
import org.junit.jupiter.api.Test;

public class AddonTest {
  private static Injector injector;
  private static MAStaffMock plugin = new MAStaffMock();
  private static AddonManager manager;
  private static EventManager eventManager;
  @Test
  public void testAddon() {
    new MockLogger();
    injector = Guice.createInjector(new TestInjector(plugin));
    plugin.setInjector(injector);
    manager = injector.getInstance(AddonManager.class);
    eventManager = injector.getInstance(EventManager.class);

    manager.enableAllAddonsFromTheClasspath();
    AddonsUtils.getAddons(manager).forEach(addon -> {
      System.out.println(addon.getName());
    });

    eventManager.fireEvent(new TestEvent("Nookure.com"));
  }
}
