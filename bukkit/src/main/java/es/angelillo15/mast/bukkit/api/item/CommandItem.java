package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandItem implements StaffItem{
    private int slot;
    private ItemStack itemStack;
    private Player player;
    private String command;

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
        Bukkit.dispatchCommand(player, command);
    }

    @Override
    public void set() {
        player.getInventory().setItem(slot, this.getItem());
    }

    public CommandItem(Player player, ItemStack itemStack, int slot, String command){
        this.player = player;
        this.itemStack = itemStack;
        this.slot = slot;
        this.command = command;
    }

}
