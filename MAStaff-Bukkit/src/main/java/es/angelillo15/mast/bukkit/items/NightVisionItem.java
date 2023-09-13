package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.items.IExecutableItem;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class NightVisionItem extends StaffItem implements IExecutableItem {
  private final ItemStack item;
  private final int slot;
  private final String permission;

  public NightVisionItem(ItemStack item, int slot, String permission) {
    this.item = item;
    this.slot = slot;
    this.permission = permission;
  }

  @Override
  public void click(Player player) {
    Bukkit.getScheduler().runTaskLater(MAStaff.getPlugin(), () -> {
      if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        TextUtils.sendMessage(player, Messages.StaffMessages.nightVisionOff());
      } else {
        player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(999999, 1));
        TextUtils.sendMessage(player, Messages.StaffMessages.nightVisionOn());
      }
    }, 1L);
  }

  @Override
  public String getName() {
    return "NIGHT_VISION";
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
