package es.angelillo15.mast.bukkit.utils;

import net.md_5.bungee.api.ChatColor;

public class TextUtils {
    public static String parseMessage(String messages){
        return ChatColor.translateAlternateColorCodes('&', messages.replace("{prefix}", Messages.PREFIX()));
    }
}
