package es.angelillo15.mast.api;

import es.angelillo15.mast.api.utils.MAStaffInject;
import es.angelillo15.mast.api.utils.ServerUtils;
import es.angelillo15.mast.api.utils.VersionUtils;
import org.bukkit.Bukkit;

public interface MAStaffInstance<P> extends MAStaffInject {
    P getPluginInstance();

    static int version() {
        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUNGEE) {
            return 19;
        } else {
            return VersionUtils.getBukkitVersion();
        }
    }
    static boolean placeholderCheck() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
    static ILogger getLogger() {
        return ILogger.getInstance();
    }

}
