package es.angelillo15.mast.api;

import es.angelillo15.mast.api.exceptions.PluginNotLoadedException;
import lombok.Getter;
import org.bukkit.Bukkit;

public interface MAStaffInstance {
    /**
     * @return the plugin instance
     *
     */
    public static MAStaffInstance getInstance(){
        MAStaffInstance plugin = (MAStaffInstance) Bukkit.getPluginManager().getPlugin("MAStaff");
        if(plugin == null){
            throw new PluginNotLoadedException("MAStaff plugin is not loaded");
        } else {
            return plugin;
        }
    }

}
