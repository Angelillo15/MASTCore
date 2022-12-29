package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.config.Messages;
import org.bukkit.ChatColor;

public class TextUtils {
    public static String parseMessage(String messages){
        return ChatColor.translateAlternateColorCodes('&', messages.replace("{prefix}", Messages.PREFIX())
        );
    }
}
