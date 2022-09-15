package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderchestItem implements StaffItem, EntityInteractItem {
    private int slot;
    private ItemStack itemStack;
    private Player target;
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

    @Override
    public Player getTarget() {
        return target;
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public void click(Player player) {

    }


    public EnderchestItem(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }
}
