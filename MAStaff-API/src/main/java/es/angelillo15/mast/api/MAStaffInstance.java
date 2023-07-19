package es.angelillo15.mast.api;

import com.velocitypowered.api.plugin.PluginManager;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.exceptions.PluginNotLoadedException;
import es.angelillo15.mast.api.utils.ServerUtils;
import es.angelillo15.mast.api.utils.VelocityUtils;
import es.angelillo15.mast.api.utils.VersionUtils;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;


public interface MAStaffInstance<P> {
    public static int version() {
        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUNGEE) {
            return 19;
        } else {
            return VersionUtils.getBukkitVersion();
        }
    }

    static MAStaffInstance<Plugin> getInstance(){
        MAStaffInstance<Plugin> instance = (MAStaffInstance<Plugin>) Bukkit.getPluginManager().getPlugin("MAStaff");
        if(instance == null){
            throw new PluginNotLoadedException("MAStaff is not loaded");
        }
        return instance;
    }

    static MAStaffInstance<net.md_5.bungee.api.plugin.Plugin> getBungeeInstance() {
        MAStaffInstance<net.md_5.bungee.api.plugin.Plugin> instance = (MAStaffInstance<net.md_5.bungee.api.plugin.Plugin>) ProxyServer
                .getInstance().getPluginManager()
                .getPlugin("MAStaff");
        if(instance == null){
            throw new PluginNotLoadedException("MAStaff is not loaded");
        }
        return instance;
    }

    static MAStaffInstance<com.velocitypowered.api.proxy.ProxyServer> getVelocityInstance() {
        return VelocityUtils.getInstance();
    }


    public static boolean placeholderCheck() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public ILogger getPLogger();
    public static ILogger getLogger() {
        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUKKIT) {
            return getInstance().getPLogger();
        }

        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUNGEE) {
            return getBungeeInstance().getPLogger();
        }

        return getVelocityInstance().getPLogger();
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
    File getPluginDataFolder();
    InputStream getPluginResource(String s);
    void setDebug(boolean debug);
    public P getPluginInstance();

}
