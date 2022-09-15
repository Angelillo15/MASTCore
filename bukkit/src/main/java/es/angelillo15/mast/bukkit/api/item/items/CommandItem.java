package es.angelillo15.mast.bukkit.api.item.items;

import es.angelillo15.mast.bukkit.api.item.types.StaffCommandItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandItem implements StaffItem, StaffCommandItem {
    private int slot;
    private ItemStack itemStack;
    private String command;

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

    public CommandItem(ItemStack itemStack, int slot, String command){
        this.itemStack = itemStack;
        this.slot = slot;
        this.command = command;
    }

    @Override
    public void click(Player player, String command) {
        player.performCommand(command);
    }

    @Override
    public String getCommand() {
        return command;
    }
}
