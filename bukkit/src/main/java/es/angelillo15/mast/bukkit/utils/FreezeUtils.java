package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.events.staff.FreezeMessageEvent;
import es.angelillo15.mast.bukkit.listeners.FreezeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FreezeUtils {
    public static void setupMessageSender(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(MASTBukkitManager.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(MASTBukkitManager.getInstance().containsFreezePlayer(player.getUniqueId())){
                    Bukkit.getPluginManager().callEvent(new FreezeMessageEvent(player));
                }
            });
        }, 0, 20*5);
    }

    public static void toggleVanish(Player staff, Player target){
        if(MASTBukkitManager.getInstance().containsFreezePlayer(target.getUniqueId())){
            MASTBukkitManager.getInstance().removeFreezePlayer(target.getUniqueId());
            target.sendMessage(Messages.GET_FREEZE_UNFROZEN_MESSAGE());
            StaffUtils.asyncStaffBroadcastMessage(Messages.GET_FREEZE_UNFROZEN_BY_MESSAGE().replace("{player}", target.getName()).replace("{staff}", staff.getName()));
        }else{
            MASTBukkitManager.getInstance().addFreezePlayer(target.getUniqueId(), target);
            target.sendMessage(Messages.GET_FREEZE_FROZEN_MESSAGE());
            StaffUtils.asyncStaffBroadcastMessage(Messages.GET_FREEZE_FROZEN_BY_MESSAGE().replace("{player}", target.getName()).replace("{staff}", staff.getName()));
        }
    }
}
