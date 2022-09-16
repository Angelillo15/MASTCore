package es.angelillo15.mast.bukkit.api.item.items;

import es.angelillo15.mast.bukkit.api.item.types.EntityInteractItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChestItem implements StaffItem, EntityInteractItem {
    private Player player;
    private Player target;
    private int slot;
    private ItemStack itemStack;

    @Override
    public Player getTarget() {
        return target;
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public void click(Player player, Player target) {
        player.openInventory(target.getInventory());
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public ItemStack getItem() {
        return itemStack;
    }

    public ChestItem (ItemStack itemStack, int slot){
        this.itemStack = itemStack;
        this.slot = slot;
    }

    @Override
    public void set(Player player) {
        player.getInventory().setItem(getSlot(), getItem());
    }
}
