package es.angelillo15.mast.bukkit.api.item.items;

import es.angelillo15.mast.bukkit.api.item.types.EntityInteractItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.annotation.Target;

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
    public void click(Player player, Player target) {
        player.openInventory(target.getEnderChest());
    }


    public EnderchestItem(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }
}
