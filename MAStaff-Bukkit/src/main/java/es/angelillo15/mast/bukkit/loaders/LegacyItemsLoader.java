package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.mast.bukkit.MAStaff;

public class LegacyItemsLoader {
  public static void load() {
    MAStaff.getPlugin().getInjector().getInstance(ItemsLoader.class).load();
  }
}
