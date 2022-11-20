package es.angelillo15.mast.bungee.config;

import es.angelillo15.mast.bungee.utils.TextUtils;
import es.angelillo15.mast.config.ConfigManager;
import net.md_5.bungee.api.ChatColor;

public class Messages {
    private static ConfigManager messages = ConfigLoader.getMessages();

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', messages.getConfig().getString("Messages.prefix"));
    }

    public static String getPlayerStaffModeEnabled() {
        return messages.getConfig().getString("Messages.playerStaffModeEnabled");
    }

    public static String getPlayerStaffModeDisabled() {
        return messages.getConfig().getString("Messages.playerStaffModeDisabled");
    }

    public static String GET_STAFF_CHAT_FORMAT() {
        return TextUtils.colorize(messages.getConfig().getString("StaffChat.format"));
    }

    public static String GET_RELOADED_MESSAGE() {
        return TextUtils.colorize(messages.getConfig().getString("Reloaded"));
    }
    public static String getStaffChangeServer(){
        return messages.getConfig().getString("StaffChangeServer.message");
    }
    public static String getStaffJoin(){
        return messages.getConfig().getString("StaffJoin.message");
    }
    public static String getStaffLeave(){
        return messages.getConfig().getString("StaffLeave.message");
    }
    public static String getStaffChatUssage(){
        return messages.getConfig().getString("StaffChat.correctUse");
    }

    public static String getStaffChatEnabled(){
        return TextUtils.colorize(messages.getConfig().getString("StaffChat.enabled"));
    }

    public static String getStaffChatDisabled(){
        return TextUtils.colorize(messages.getConfig().getString("StaffChat.disabled")) ;
    }

    public static void reload() {
        messages = ConfigLoader.getMessages();
    }

}
