package es.angelillo15.mast.bukkit.api.item;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.TextUtils;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class VanishItem implements StaffItem{
    private int slot;
    private ItemStack itemStack;
    private Player player;

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
        VanishUtils.toggle(player);
        BStaffPlayer staffPlayer = MASTBukkitManager.getInstance().getSStaffPlayer(player.getUniqueId());
        if (staffPlayer.isVanished()){
            player.sendMessage(TextUtils.parseMessage(Messages.GET_VANISH_ENABLE_MESSAGE()));
        } else {
            player.sendMessage(TextUtils.parseMessage(Messages.GET_VANISH_DISABLE_MESSAGE()));
        }
    }

    @Override
    public void set() {
        player.getInventory().setItem(slot, this.getItem());
    }

    public VanishItem(Player player, ItemStack itemStack, int slot){
        this.player = player;
        this.itemStack = itemStack;
        this.slot = slot;
    }

}
