package com.nookure.staff.paper.item;

import com.google.inject.Inject;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.item.Items;
import com.nookure.staff.api.item.PlayerInteractItem;
import com.nookure.staff.api.item.StaffItem;
import org.jetbrains.annotations.NotNull;

public class EnderchestItem extends StaffItem implements PlayerInteractItem {
  @Inject
  public EnderchestItem(ConfigurationContainer<ItemsConfig> itemsConfig) {
    super(itemsConfig.get().staffItems.getItems().get(Items.ENDER_CHEST.toString()));
  }

  @Override
  public void click(@NotNull PlayerWrapper player, @NotNull PlayerWrapper target) {

  }
}