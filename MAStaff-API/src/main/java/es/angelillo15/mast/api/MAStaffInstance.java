package es.angelillo15.mast.api;

import es.angelillo15.mast.api.exceptions.PluginNotLoadedException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public interface MAStaffInstance {
    public static final int version = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);

    public static MAStaffInstance getInstance(){
        MAStaffInstance instance = (MAStaffInstance) Bukkit.getPluginManager().getPlugin("MAStaff");
        if(instance == null){
            throw new PluginNotLoadedException("MAStaff is not loaded");
        }
        return instance;
    }

    public static boolean placeholderCheck() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public ILogger getPLogger();
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
    public IStaffPlayer createStaffPlayer(Player player);

}
