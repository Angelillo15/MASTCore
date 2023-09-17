package com.nookure.mast.tests.addon;

import com.nookure.mast.api.addons.AddonActions;
import com.nookure.mast.api.addons.annotations.Addon;

@Addon(
    author = "Nookure",
    name = "Addon1",
    version = "1.0.0",
    description = "Addon1 description",
    platform = Addon.AddonPlatform.COMMON,
    listeners = {
        TestListener.class,
    }
)
public class Addon1 implements AddonActions {

  @Override
  public void onEnable() {
    System.out.println("Addon1 enabled");
  }

  @Override
  public void onDisable() {
    System.out.println("Addon1 disabled");
  }
}
