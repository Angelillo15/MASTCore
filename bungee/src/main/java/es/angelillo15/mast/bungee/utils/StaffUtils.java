package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import net.md_5.bungee.api.chat.TextComponent;

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

    public static void sendBroadcastPermissionMessage(String message, String permission){
        MASTBungeeManager.getInstance().getProxy().getScheduler().runAsync(MASTBungeeManager.getInstance(), () -> {
            MASTBungeeManager.getInstance().getProxy().getPlayers().forEach(player -> {
                if(player.hasPermission(permission)){
                    player.sendMessage(new TextComponent(TextUtils.colorize(message)));
                }
            });
        });
        MASTBungeeManager.getInstance().getLogger().info(TextUtils.colorize(message));
    }
}
