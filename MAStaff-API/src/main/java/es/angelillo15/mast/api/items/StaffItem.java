package es.angelillo15.mast.api.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class StaffItem {
    public abstract String getName();
    public abstract ItemStack getItem();
    public abstract String getPermission();
    public abstract int getSlot();
    public abstract void setItem(Player player);
}
