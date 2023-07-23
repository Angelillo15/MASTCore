package es.angelillo15.mast.api.utils;

import com.google.inject.Injector;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.cmd.Command;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStream;

public interface MAStaffInject {
    ILogger getPLogger();
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
    default Injector getInjector() {
        return null;
    }
}
