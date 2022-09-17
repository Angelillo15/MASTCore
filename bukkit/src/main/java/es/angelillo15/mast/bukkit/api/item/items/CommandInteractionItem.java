package es.angelillo15.mast.bukkit.api.item.items;

import es.angelillo15.mast.bukkit.api.item.types.ExecutableInteractionItems;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandInteractionItem implements StaffItem, ExecutableInteractionItems {
    private int slot;
    private ItemStack itemStack;
    private String command;
    @Override
    public void click(Player player, Player target) {
        player.performCommand(command.replace("{target}", target.getName()));
    }

    @Override
    public String getCommand() {
        return command;
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

    public CommandInteractionItem(ItemStack itemStack, int slot, String command){
        this.itemStack = itemStack;
        this.slot = slot;
        this.command = command;
    }
}
