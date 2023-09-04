package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.ICommandItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandItem extends StaffItem implements ICommandItem {
  private final String command;
  private final ItemStack item;
  private final int slot;
  private final String permission;

  public CommandItem(ItemStack item, int slot, String command, String permission) {
    this.item = item;
    this.slot = slot;
    this.command = command;
    this.permission = permission;
  }

  @Override
  public void execute(Player player, String command) {
    player.performCommand(command);
  }

  @Override
  public void execute(Player player) {
    player.performCommand(command);
  }

  @Override
  public String getName() {
    return "COMMAND_ITEM";
  }

  @Override
  public ItemStack getItem() {
    return item;
  }

  @Override
  public void setItem(Player player) {
    player.getInventory().setItem(getSlot(), getItem());
  }

  @Override
  public String getPermission() {
    return permission;
  }

  @Override
  public int getSlot() {
    return slot;
  }
}
