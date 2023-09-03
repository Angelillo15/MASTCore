package es.angelillo15.mast.api.utils;

import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermsUtils {
  @Getter private static Permission perms = null;
  @Getter private static boolean vaultEnabled = false;

  public static boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp =
        Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

    assert rsp != null;

    perms = rsp.getProvider();
    vaultEnabled = true;
    return perms != null;
  }

  public static String getGroup(Player player) {
    if (perms == null) return "";
    return perms.getPrimaryGroup(player);
  }
}
