package com.nookure.staff.api.item;

import com.nookure.staff.api.config.bukkit.partials.ItemPartial;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Represents a staff item that can be added to the user's inventory.
 * this staff item does not have any functionality until it implements
 * an interface that represents a staff action.
 *
 * @since 1.0.0
 */
public abstract class StaffItem {
  private final ItemPartial itemConfig;
  private final ItemStack itemStack;

  public StaffItem(ItemPartial itemConfig) {
    this.itemConfig = itemConfig;
    ItemStack itemStack = new ItemStack(itemConfig.getMaterial());
    ItemMeta meta = itemStack.getItemMeta();

    meta.displayName(Component.text(itemConfig.getName()));
    meta.lore(itemConfig.lore());

    itemStack.setItemMeta(meta);

    this.itemStack = itemStack;
  }

  public void setItem(Player playerWrapper) {
    playerWrapper.getInventory().setItem(itemConfig.getSlot(), itemStack);
  }

  public String getName() {
    return itemConfig.getName();
  }

  public String getPermission() {
    return itemConfig.getPermission();
  }

  public List<Component> getLore() {
    return itemConfig.lore();
  }

  public ItemPartial getItemConfig() {
    return itemConfig;
  }
}
