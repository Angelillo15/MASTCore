package es.angelillo15.mast.bukkit.api.item.items;

import es.angelillo15.mast.bukkit.api.item.types.ExecutableItem;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerRandomTPItem implements StaffItem, ExecutableItem {

    private int slot;
    private ItemStack itemStack;

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public ItemStack getItem() {
        return itemStack;
    }

    @Override
    public void click(Player player) {
        StaffUtils.playerRandomTeleport(player);
    }

    @Override
    public void set(Player player) {
        player.getInventory().setItem(slot, this.getItem());
    }


    public PlayerRandomTPItem(ItemStack itemStack, int slot){
        this.itemStack = itemStack;
        this.slot = slot;
    }
}
