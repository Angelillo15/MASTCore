package es.angelillo15.mast.api.gui;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Page {
    @Getter
    private HashMap<Integer, ItemStack> items = new HashMap<>();

    public Page(HashMap<Integer, ItemStack> items){
        this.items = items;
    }

    public void addItem(int slot, ItemStack itemStack){
        items.put(slot, itemStack);
    }

    public void removeItem(int slot){
        items.remove(slot);
    }

    public ItemStack getItem(int slot){
        return items.get(slot);
    }

    public boolean containsItem(int slot){
        return items.containsKey(slot);
    }

    public void clear(){
        items.clear();
    }
}
