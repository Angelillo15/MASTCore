package es.angelillo15.mast.api.managers;

import es.angelillo15.mast.api.items.StaffItem;
import lombok.Getter;

import java.util.ArrayList;

public class ItemManager {
    @Getter
    private ArrayList<StaffItem> items = new ArrayList<>();

    /**
     * @param item The item to add
     */
    public void addItem(StaffItem item){
        items.add(item);
    }

    /**
     * @param item The item to remove
     */
    public void removeItem(StaffItem item){
        items.remove(item);
    }

    /**
     * @param name The name of the item to get
     * @return The item
     */
    public StaffItem getItem(String name){
        for(StaffItem item : items){
            if(item.getName().equalsIgnoreCase(name)) return item;
        }
        return null;
    }

    /**
     * Clear all items
     */
    public void clearItems() {
        items.clear();
    }
}
