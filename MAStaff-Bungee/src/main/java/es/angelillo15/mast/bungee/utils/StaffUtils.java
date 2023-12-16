package es.angelillo15.mast.bungee.utils;

import com.nookure.mas.bungee.MAStaff;
import net.md_5.bungee.api.chat.TextComponent;

public class StaffUtils {
    public static void sendStaffChatMessage(String message){
        MAStaff.getInstance().getProxy().getScheduler().runAsync(MAStaff.getInstance(), () -> {
            MAStaff.getInstance().getProxy().getPlayers().forEach(player -> {
                if(player.hasPermission("mast.staffchat")){
                    player.sendMessage(new TextComponent(message));
                }
            });
        });

        MAStaff.getInstance().getPLogger().info(message);
    }

    public static void sendBroadcastPermissionMessage(String message, String permission){
        MAStaff.getInstance().getProxy().getScheduler().runAsync(MAStaff.getInstance(), () -> {
            MAStaff.getInstance().getProxy().getPlayers().forEach(player -> {
                if(player.hasPermission(permission)){
                    player.sendMessage(new TextComponent(TextUtils.colorize(message)));
                }
            });
        });
        MAStaff.getInstance().getLogger().info(TextUtils.colorize(message));
    }
}
