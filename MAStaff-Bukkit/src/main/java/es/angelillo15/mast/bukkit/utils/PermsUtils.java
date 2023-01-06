package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.MAStaff;
import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermsUtils {
    @Getter
    private static Permission perms = null;
    @Getter
    private static boolean vaultEnabled = false;

    public static boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = MAStaff.getPlugin().getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        vaultEnabled = true;
        return perms != null;
    }

    public static String getGroup(Player player) {
        return perms.getPrimaryGroup(player);
    }
}
