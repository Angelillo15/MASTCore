package com.nookure.staff.paper.listener.staff.items;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.item.StaffItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public abstract class CommonPlayerInteraction {
  private static final Cache<UUID, Long> itemsCache = Caffeine.newBuilder()
      .expireAfterAccess(Duration.of(500, ChronoUnit.MILLIS))
      .build();

  public Optional<StaffItem> getItem(ItemStack item, StaffPlayerWrapper player) {
    if (!item.hasItemMeta()) {
      return Optional.empty();
    }

    PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();

    if (container.has(StaffItem.key, PersistentDataType.INTEGER)) {
      return Optional.ofNullable(player.getItems().get(container.get(StaffItem.key, PersistentDataType.INTEGER)));
    }

    return Optional.empty();
  }

  public boolean canUseItem(StaffPlayerWrapper player, StaffItem item) {
    if (!player.hasPermission(item.getPermission())) {
      return false;
    }

    if (itemsCache.getIfPresent(player.getUniqueId()) != null) {
      return false;
    }

    itemsCache.put(player.getUniqueId(), System.currentTimeMillis());

    return true;
  }
}
