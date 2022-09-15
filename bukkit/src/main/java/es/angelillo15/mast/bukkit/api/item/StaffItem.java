package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public interface StaffItem {
    int getSlot();
    ItemStack getItem();
    void set(Player player);

}
