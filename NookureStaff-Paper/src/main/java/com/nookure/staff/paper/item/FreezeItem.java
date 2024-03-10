package com.nookure.staff.paper.item;

import com.google.inject.Inject;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.item.Items;
import com.nookure.staff.api.item.PlayerInteractItem;
import com.nookure.staff.api.item.StaffItem;
import com.nookure.staff.api.manager.FreezeManager;
import com.nookure.staff.paper.extension.FreezePlayerExtension;
import org.jetbrains.annotations.NotNull;

public class FreezeItem extends StaffItem implements PlayerInteractItem {
  private final FreezeManager freezeManager;

  @Inject
  public FreezeItem(ConfigurationContainer<ItemsConfig> itemsConfig, FreezeManager freezeManager) {
    super(itemsConfig.get().staffItems.getItems().get(Items.FREEZE.toString()));
    this.freezeManager = freezeManager;
  }

  @Override
  public void click(@NotNull PlayerWrapper player, @NotNull PlayerWrapper target) {
    if (player instanceof StaffPlayerWrapper staff) {
      staff.getExtension(FreezePlayerExtension.class).ifPresent(freezePlayerExtension -> {
        if (freezeManager.isFrozen(target)) {
          freezePlayerExtension.unfreezePlayer(target);
          return;
        }

        freezePlayerExtension.freezePlayer(target);
      });
    }
  }
}
