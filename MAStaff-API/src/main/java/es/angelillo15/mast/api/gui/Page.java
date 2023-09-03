package es.angelillo15.mast.api.gui;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class Page {
  @Getter private HashMap<Integer, CommandItem> items = new HashMap<>();
  @Getter @Setter private String title;

  public Page(HashMap<Integer, CommandItem> items) {
    this.items = items;
  }

  public void addItem(int slot, CommandItem CommandItem) {
    items.put(slot, CommandItem);
  }

  public void removeItem(int slot) {
    items.remove(slot);
  }

  public CommandItem getItem(int slot) {
    return items.get(slot);
  }

  public boolean containsItem(int slot) {
    return items.containsKey(slot);
  }

  public void clear() {
    items.clear();
  }
}
