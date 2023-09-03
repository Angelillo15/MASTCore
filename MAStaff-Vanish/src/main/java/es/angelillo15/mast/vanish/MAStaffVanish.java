package es.angelillo15.mast.vanish;

import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.utils.ServerUtils;
import es.angelillo15.mast.vanish.listeners.VanishListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MAStaffVanish extends MAStaffAddon<JavaPlugin> {
  @Getter private static boolean useProtocolLib;
  @Getter private static MAStaffVanish instance;

  @Override
  public void onEnable() {
    getLogger().info("Enabling MAStaff Vanish module...");
    instance = this;
    useProtocolLib = ServerUtils.isProtocolLibInstalled();
    registerListeners();
  }

  void registerListeners() {
    getPluginInstance()
        .getServer()
        .getPluginManager()
        .registerEvents(new VanishListener(), getPluginInstance());
  }
}
