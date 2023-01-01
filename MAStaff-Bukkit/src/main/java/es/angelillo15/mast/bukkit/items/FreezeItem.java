package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.IExecutableItem;
import es.angelillo15.mast.api.items.IPlayerInteractItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FreezeItem extends StaffItem implements IExecutableItem {
    private ItemStack item;
    private int slot;
    private String permission;

    public FreezeItem(ItemStack item, int slot, String permission) {
        this.item = item;
        this.slot = slot;
        this.permission = permission;
    }

    @Override
    public String getName() {
        return "FREEZE";
    }

    @Override
    public ItemStack getItem() {
        return item;
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
    public void setItem(Player player) {
        player.getInventory().setItem(getSlot(), getItem());
    }

    @Override
    public void click(Player player) {

    }
}
