package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.bungee.MASTBungeeManager;

public class StaffUtils {
    public static void sendStaffChatMessage(String message){
        MASTBungeeManager.getInstance().getProxy().getScheduler().runAsync(MASTBungeeManager.getInstance(), () -> {
            MASTBungeeManager.getInstance().getProxy().getPlayers().forEach(player -> {
                if(player.hasPermission("mast.staffchat")){
                    player.sendMessage(message);
                }
            });
        });
    }
}
