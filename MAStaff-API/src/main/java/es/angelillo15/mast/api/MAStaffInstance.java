package es.angelillo15.mast.api;

import com.google.inject.Injector;
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

    ILogger getPLogger();
    static ILogger getLogger() {
        return ILogger.getInstance();
    }

    default void registerCommand(Command command){};
    IServerUtils getServerUtils();
    boolean isDebug();
    void drawLogo();
    void loadConfig();
    void registerCommands();
    void registerListeners();
    void loadDatabase();
    void loadModules();
    void unregisterCommands();
    void unregisterListeners();
    void unloadDatabase();
    void reload();
    default IStaffPlayer createStaffPlayer(Player player) { return null; }
    File getPluginDataFolder();
    InputStream getPluginResource(String s);
    void setDebug(boolean debug);
    P getPluginInstance();
    default Injector getInjector() {
        return null;
    }


}
