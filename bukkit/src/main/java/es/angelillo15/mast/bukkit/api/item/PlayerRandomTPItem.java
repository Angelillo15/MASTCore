package es.angelillo15.mast.bukkit.api.item;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import es.angelillo15.mast.bukkit.utils.TextUtils;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerRandomTPItem implements StaffItem, ExecutableItem{

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
        Bukkit.getScheduler().runTaskAsynchronously(MASTBukkitManager.getInstance(), () -> {
            StaffUtils.playerRandomTeleport(player);
        });
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
