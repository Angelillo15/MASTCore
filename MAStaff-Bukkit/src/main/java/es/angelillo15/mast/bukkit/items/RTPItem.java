package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.IExecutableItem;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RTPItem extends StaffItem implements IExecutableItem {
  private final ItemStack item;
  private final int slot;
  private final String permission;

  public RTPItem(ItemStack item, int slot, String permission) {
    this.item = item;
    this.slot = slot;
    this.permission = permission;
  }

  @Override
  public String getName() {
    return "RANDOM_PLAYER_TELEPORT";
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

  @Override
  public void click(Player player) {
    StaffUtils.playerRandomTeleport(player);
  }
}
