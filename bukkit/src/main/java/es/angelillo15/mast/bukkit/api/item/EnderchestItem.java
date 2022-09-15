package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderchestItem implements StaffItem, EntityInteractItem {
    private int slot;
    private Player player;
    private ItemStack itemStack;
    private Player target;
    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public ItemStack getItem() {
        return itemStack;
    }

    @Override
    public void click() {

    }

    @Override
    public void set() {
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


    public EnderchestItem(Player player, ItemStack itemStack, int slot) {
        this.player = player;
        this.itemStack = itemStack;
        this.slot = slot;
    }
}
