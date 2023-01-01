package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.api.items.IPlayerInteractItem;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandInteractionItem extends StaffItem implements IPlayerInteractItem {
    private int slot;
    private ItemStack item;
    private String command;
    private String permission;

    public CommandInteractionItem(ItemStack item, int slot, String command, String permission) {
        this.item = item;
        this.slot = slot;
        this.command = command;
        this.permission = permission;
    }

    @Override
    public void interact(Player player, Player target) {
        player.performCommand(command.replace("{target}", target.getName()));
    }

    @Override
    public String getName() {
        return "COMMAND_INTERACTION";
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

    }
}
