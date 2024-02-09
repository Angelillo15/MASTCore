package com.nookure.staff.paper.item;

import com.google.inject.Inject;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.item.ExecutableLocationItem;
import com.nookure.staff.api.item.Items;
import com.nookure.staff.api.item.StaffItem;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ThruItem extends StaffItem implements ExecutableLocationItem {
  @Inject
  public ThruItem(ConfigurationContainer<ItemsConfig> itemsConfig) {
    super(itemsConfig.get().staffItems.getItems().get(Items.THRU.toString()));
  }

  @Override
  public void click(@NotNull PlayerWrapper player, @NotNull Location location) {

  }
}
