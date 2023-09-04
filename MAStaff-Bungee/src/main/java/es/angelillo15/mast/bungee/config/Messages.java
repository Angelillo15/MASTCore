package es.angelillo15.mast.bungee.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.mast.bungee.utils.TextUtils;
import java.util.ArrayList;
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

    public static String getHelpopMessage(){
        return messages.getConfig().getString("Helpop.message");
    }

    public static String getHelpopFormat(){
        return messages.getConfig().getString("Helpop.format");
    }

    public static String getHelpopCorrectUse(){
        return messages.getConfig().getString("Helpop.correctUse");
    }

    public static String getHelpopCooldown(){
        return messages.getConfig().getString("Helpop.cooldown");
    }

    public static void reload() {
        messages = ConfigLoader.getMessages();
    }

    public static ArrayList<String> getInfoMessage() {
        ArrayList<String> colorized = new ArrayList<>();
        for (String s : messages.getConfig().getStringList("Info.playerInfoMessage")) {
            colorized.add(TextUtils.colorize(s));
        }
        return colorized;
    }

    public static String getInfoUserNotFound(){
        return messages.getConfig().getString("Info.playerNotFound");
    }
}
