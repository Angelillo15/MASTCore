package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.MAStaffBukkitManager;
import es.angelillo15.mast.bukkit.api.events.staff.FreezeMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FreezeUtils {
    public static void setupMessageSender(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(MAStaffBukkitManager.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(MAStaffBukkitManager.getInstance().containsFreezePlayer(player.getUniqueId())){
                    Bukkit.getPluginManager().callEvent(new FreezeMessageEvent(player));
                }
            });
        }, 0, 20*5);
    }

    public static void toggleVanish(Player staff, Player target){
        if(MAStaffBukkitManager.getInstance().containsFreezePlayer(target.getUniqueId())){
            MAStaffBukkitManager.getInstance().removeFreezePlayer(target.getUniqueId());
            target.sendMessage(Messages.GET_FREEZE_UNFROZEN_MESSAGE());
            StaffUtils.asyncStaffBroadcastMessage(Messages.GET_FREEZE_UNFROZEN_BY_MESSAGE().replace("{player}", target.getName()).replace("{staff}", staff.getName()));
        }else{
            MAStaffBukkitManager.getInstance().addFreezePlayer(target.getUniqueId(), target);
            target.sendMessage(Messages.GET_FREEZE_FROZEN_MESSAGE());
            StaffUtils.asyncStaffBroadcastMessage(Messages.GET_FREEZE_FROZEN_BY_MESSAGE().replace("{player}", target.getName()).replace("{staff}", staff.getName()));
        }
    }
}
