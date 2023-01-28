package es.angelillo15.mast.api.gui;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class CommandItem extends ItemStack {
    @Getter
    private String command;

    public CommandItem(ItemStack itemStack, String command) {
        super(itemStack);
        this.command = command;
    }
}
