package es.angelillo15.mast.bukkit.api.item.items;

import es.angelillo15.mast.bukkit.api.item.types.ExecutableInteractionItems;
import es.angelillo15.mast.bukkit.api.item.types.ExecutableThruItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import es.angelillo15.mast.bukkit.utils.items.PassThrough;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ThruItem implements StaffItem, ExecutableInteractionItems ,ExecutableThruItem {
    private ItemStack itemStack;
    private int slot;


    @Override
    public void click(Player player, Location clickLocation) {
        PassThrough.passThrough(player, clickLocation);
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public ItemStack getItem() {
        return itemStack;
    }

    @Override
    public void set(Player player) {
        player.getInventory().setItem(slot, this.getItem());
    }

    public ThruItem(ItemStack itemStack, int slot){
        this.itemStack = itemStack;
        this.slot = slot;
    }

    @Override
    public void click(Player player, Player target) {

    }

    @Override
    public String getCommand() {
        return null;
    }
}
