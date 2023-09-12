package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.mast.bukkit.MAStaff;

public class LegacyCustomItemsLoader {

  public static void load() {
    MAStaff.getPlugin().getInjector().getInstance(CustomItemsLoader.class).load();
  }
}
