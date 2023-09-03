package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.IExecutableLocationItem;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ThruItem extends StaffItem implements IExecutableLocationItem {
    private ItemStack item;
    private int slot;
    private String permission;

    public ThruItem(ItemStack item, int slot, String permission) {
        this.item = item;
        this.slot = slot;
        this.permission = permission;
    }

    @Override
    public String getName() {
        return "THRU";
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
    public void click(Player player, Location location) {
        StaffUtils.passThrough(player, location);
    }
}
