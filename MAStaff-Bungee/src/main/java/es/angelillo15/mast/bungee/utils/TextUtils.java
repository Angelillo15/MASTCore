package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.bungee.config.Messages;
import net.md_5.bungee.api.ChatColor;

public class TextUtils {
    public static String colorize(String messages) {
        return ChatColor.translateAlternateColorCodes('&', messages).replace("{prefix}", Messages.getPrefix());
    }
}
