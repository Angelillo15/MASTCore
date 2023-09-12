package es.angelillo15.mast.vanish;

import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.vanish.listeners.VanishListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MAStaffVanish extends MAStaffAddon<JavaPlugin> {
  @Getter private static MAStaffVanish instance;

  @Override
  public void onEnable() {
    getLogger().info("Enabling MAStaff Vanish module...");
    instance = this;
    registerListeners();
  }

  void registerListeners() {
    getPluginInstance()
        .getServer()
        .getPluginManager()
        .registerEvents(getInjector().getInstance(VanishListener.class), getPluginInstance());
  }
}
