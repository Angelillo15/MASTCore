package es.angelillo15.mast.bukkit.items.custom;

import es.angelillo15.mast.api.items.IExecutableItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomCommandItem extends StaffItem implements IExecutableItem {
  private final String command;
  private final String permission;
  private final String name;
  private final ItemStack itemStack;
  private final int slot;

  public CustomCommandItem(
      String name, ItemStack itemStack, int slot, String permission, String command) {
    this.name = name;
    this.itemStack = itemStack;
    this.slot = slot;
    this.permission = permission;
    this.command = command;
  }

  @Override
  public void click(Player player) {
    player.performCommand(this.command.replace("{player}", player.getName()));
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public ItemStack getItem() {
    return itemStack;
  }

  @Override
  public void setItem(Player player) {
    player.getInventory().setItem(this.slot, this.itemStack);
  }

  @Override
  public String getPermission() {
    return this.permission;
  }

  @Override
  public int getSlot() {
    return this.slot;
  }
}
