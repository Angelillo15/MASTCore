package es.angelillo15.mast.bukkit.utils;


import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishDisableEvent;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class VanishUtils {
    private static MASTBukkitManager plugin = MASTBukkitManager.getInstance();

    public static void enableVanish(Player player){
        for (Player people : Bukkit.getOnlinePlayers()){
            if (people.hasPermission("mast.vanish.see")){
                people.showPlayer(player);
            } else {
                people.hidePlayer(player);
            }
        }
        plugin.addVanishedPlayer(plugin.getSStaffPlayer(player.getUniqueId()));
    }

    public static void disableVanish(Player player){
        for (Player people : Bukkit.getOnlinePlayers()){
            if (!(people.hasPermission("mast.vanish.see"))){
                people.showPlayer(player);
            }
        }
        plugin.removeVanishedPlayer(plugin.getSStaffPlayer(player.getUniqueId()));
    }

    public static void toggle(Player player){
        PluginManager pm = plugin.getServer().getPluginManager();
        if(plugin.getVanishedPlayers().containsKey(player.getUniqueId())){
            pm.callEvent(new PlayerVanishDisableEvent(player));
        } else {
            pm.callEvent(new PlayerVanishEnableEvent(player));
        }
    }



}
