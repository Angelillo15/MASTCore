package es.angelillo15.mast.api.managers;

import com.google.inject.Singleton;
import es.angelillo15.mast.api.items.StaffItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ItemsManager {
  private final Map<String, StaffItem> items = new HashMap<>();

  /**
   * Add an item to the manager
   * @param item The item to add
   */
  public void addItem(StaffItem item, String name) {
    items.put(name, item);
  }

  /**
   * Remove an item from the manager
   * @param item The item to remove
   */
  public void removeItem(StaffItem item) {
    items.remove(item.getName());
  }

  /**
   * Get an item from the manager
   * @param name The name of the item to get
   * @return The item
   */
  public StaffItem getItem(String name) {
    return items.get(name);
  }

  /** Clear all items */
  public void clearItems() {
    items.clear();
  }

  /**
   * Get all items
   * @return The items
   */
  public Map<String, StaffItem> getItems() {
    return items;
  }

  /**
   * Get all items
   * @return The items
   */
  public Collection<StaffItem> getItemsSet() {
    return items.values();
  }
}
