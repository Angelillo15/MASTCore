package es.angelillo15.mast.bukkit.utils;


import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishDisableEvent;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import es.angelillo15.mast.database.SQLQueries;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class VanishUtils {
    private static MASTBukkitManager plugin = MASTBukkitManager.getInstance();

    public static void enableVanish(Player player){
        BStaffPlayer staffPlayer;

        if(plugin.getVanishedPlayers().containsKey(player.getUniqueId())) {
            staffPlayer = plugin.getVanishedPlayers().get(player.getUniqueId());
        } else {
            staffPlayer = new BStaffPlayer(player);
            plugin.addStaffPlayer(staffPlayer);
        }

        for (Player people : Bukkit.getOnlinePlayers()){
            if (people.hasPermission("mast.vanish.see")){
                people.showPlayer(player);
            } else {
                people.hidePlayer(player);
            }
        }

        plugin.addVanishedPlayer(staffPlayer);
    }

    public static void disableVanish(Player player){
        for (Player people : Bukkit.getOnlinePlayers()){
            if (!(people.hasPermission("mast.vanish.see"))){
                people.showPlayer(player);
            }
        }
        if(plugin.getSStaffPlayer(player.getUniqueId()) != null){
            plugin.removeStaffPlayer(plugin.getSStaffPlayer(player.getUniqueId()));
        }
    }

    public static void toggle(Player player){
        PluginManager pm = plugin.getServer().getPluginManager();
        BStaffPlayer staffPlayer = plugin.getSStaffPlayer(player.getUniqueId());

        if(plugin.getVanishedPlayers().containsKey(player.getUniqueId())){
            pm.callEvent(new PlayerVanishDisableEvent(player));
            staffPlayer.setVanished(false);
        } else {
            pm.callEvent(new PlayerVanishEnableEvent(player));
            staffPlayer.setVanished(true);
        }
    }

    public static void checkForStaffPlayers(){
        Bukkit.getServer().getScheduler().runTaskAsynchronously(MASTBukkitManager.getInstance(), () ->{
           for (Player player : Bukkit.getOnlinePlayers()){
               if(player.hasPermission("mast.staff.use")){
                   if(SQLQueries.getState(MASTBukkitManager.getInstance().getPluginConnection().getConnection(), player.getUniqueId()) == 1){
                       if (!plugin.getStaffPlayers().containsKey(player.getUniqueId())){
                           plugin.addStaffPlayer(new BStaffPlayer(player));
                       }
                   }
               }
           }
        });
    }



}
