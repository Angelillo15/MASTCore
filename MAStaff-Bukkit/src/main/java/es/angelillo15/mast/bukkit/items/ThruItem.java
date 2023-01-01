package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.IExecutableLocationItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ThruItem extends StaffItem implements IExecutableLocationItem {
    private ItemStack item;
    private int slot;

    public ThruItem(ItemStack item, int slot) {
        this.item = item;
        this.slot = slot;
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
    public int getSlot() {
        return slot;
    }

    @Override
    public void setItem(Player player) {
        player.getInventory().setItem(getSlot(), getItem());
    }

    @Override
    public void click(Player player, Location location) {

    }
}
