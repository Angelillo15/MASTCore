package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.ICommandItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandItem extends StaffItem implements ICommandItem {
    private String command;
    private ItemStack item;
    private int slot;
    private String permission;
    @Override
    public void execute(Player player, String command) {
        player.performCommand(command);
    }

    @Override
    public void execute(Player player) {
        player.performCommand(command);
    }

    @Override
    public String getName() {
        return "COMMAND_ITEM";
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void setItem(Player player) {
        player.getInventory().setItem(getSlot(), getItem());
    }

    public CommandItem(ItemStack item, int slot, String command, String permission) {
        this.item = item;
        this.slot = slot;
        this.command = command;
        this.permission = permission;
    }


}
