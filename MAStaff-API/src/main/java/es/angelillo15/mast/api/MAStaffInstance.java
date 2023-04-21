package es.angelillo15.mast.api;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.exceptions.PluginNotLoadedException;
import es.angelillo15.mast.api.utils.ServerUtils;
import es.angelillo15.mast.api.utils.VersionUtils;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public interface MAStaffInstance<P> {
    public static int version() {
        return VersionUtils.getBukkitVersion();
    }

    public static MAStaffInstance<Plugin> getInstance(){
        MAStaffInstance<Plugin> instance = (MAStaffInstance<Plugin>) Bukkit.getPluginManager().getPlugin("MAStaff");
        if(instance == null){
            throw new PluginNotLoadedException("MAStaff is not loaded");
        }
        return instance;
    }

    public static MAStaffInstance<net.md_5.bungee.api.plugin.Plugin> getBungeeInstance() {
        MAStaffInstance<net.md_5.bungee.api.plugin.Plugin> instance = (MAStaffInstance<net.md_5.bungee.api.plugin.Plugin>) ProxyServer
                .getInstance().getPluginManager()
                .getPlugin("MAStaff");
        if(instance == null){
            throw new PluginNotLoadedException("MAStaff is not loaded");
        }
        return instance;
    }

    public static boolean placeholderCheck() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public ILogger getPLogger();
    public static ILogger getLogger() {
        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUKKIT) {
            return getInstance().getPLogger();
        } else {
            return getBungeeInstance().getPLogger();
        }
    }
    public default void registerCommand(Command command){};
    public IServerUtils getServerUtils();
    public boolean isDebug();
    public void drawLogo();
    public void loadConfig();
    public void registerCommands();
    public void registerListeners();
    public void loadDatabase();
    public void loadModules();
    public void unregisterCommands();
    public void unregisterListeners();
    public void unloadDatabase();
    public void reload();
    public default IStaffPlayer createStaffPlayer(Player player) { return null; }
    public P getPluginInstance();

}
