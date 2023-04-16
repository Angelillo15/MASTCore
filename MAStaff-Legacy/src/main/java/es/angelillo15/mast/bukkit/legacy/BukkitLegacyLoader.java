package es.angelillo15.mast.bukkit.legacy;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.bukkit.legacy.listener.OnAchievementLegacy;
import org.bukkit.plugin.PluginManager;

public class BukkitLegacyLoader {
    public void load() {
        loadListeners();
    }

    public void unLoad(){

    }

    public void loadListeners(){
        PluginManager pm = MAStaffInstance.getInstance().getPluginInstance().getServer().getPluginManager();
        pm.registerEvents(new OnAchievementLegacy(), MAStaffInstance.getInstance().getPluginInstance());
    }
}