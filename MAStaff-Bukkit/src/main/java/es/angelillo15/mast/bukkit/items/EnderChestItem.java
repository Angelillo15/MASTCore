package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.IPlayerInteractItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderChestItem extends StaffItem implements IPlayerInteractItem {
    private ItemStack item;
    private int slot;
    private String permission;
    public EnderChestItem(ItemStack item, int slot, String permission) {
        this.item = item;
        this.slot = slot;
        this.permission = permission;
    }

    @Override
    public void interact(Player player, Player target) {
        player.openInventory(target.getEnderChest());
    }

    @Override
    public String getName() {
        return "ENDER_CHEST";
    }

    @Override
    public ItemStack getItem() {
        return this.item;
    }

    @Override
    public void setItem(Player player) {
        player.getInventory().setItem(getSlot(), getItem());
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public String getPermission() {
        return permission;
    }
}
