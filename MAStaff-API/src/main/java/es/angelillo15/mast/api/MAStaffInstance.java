package es.angelillo15.mast.api;

import es.angelillo15.mast.api.exceptions.PluginNotLoadedException;
import org.bukkit.Bukkit;


public interface MAStaffInstance {
    public static MAStaffInstance getInstance(){
        MAStaffInstance instance = (MAStaffInstance) Bukkit.getPluginManager().getPlugin("MAStaff");
        if(instance == null){
            throw new PluginNotLoadedException("MAStaff is not loaded");
        }
        return instance;
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

}
