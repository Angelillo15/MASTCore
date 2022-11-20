package es.angelillo15.mast.bukkit.api.item.items;

import es.angelillo15.mast.bukkit.api.item.types.ExecutableItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import es.angelillo15.mast.bukkit.vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VanishItem implements StaffItem, ExecutableItem {
    private int slot;
    private ItemStack itemStack;


    @Override
    public void click(Player player) {
        VanishManager.toggleVanish(player);

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

    public VanishItem(ItemStack itemStack, int slot){
        this.itemStack = itemStack;
        this.slot = slot;
    }
}
