package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.IExecutableItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RTPItem extends StaffItem implements IExecutableItem {
    private ItemStack item;
    private int slot;

    public RTPItem(ItemStack item, int slot) {
        this.item = item;
        this.slot = slot;
    }

    @Override
    public String getName() {
        return "RTP";
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
    public void click(Player player) {

    }
}
