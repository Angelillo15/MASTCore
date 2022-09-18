package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.config.ConfigLoader;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.ArrayList;

public class Messages {
    private static YamlFile messages = ConfigLoader.getMessages().getConfig();

    public static String GET_STAFF_MODE_ENABLE_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("StaffMode.toggledOn"));
    }

    public static String GET_STAFF_MODE_DISABLE_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("StaffMode.toggledOff"));
    }

    public static String GET_NO_PLAYER_ONLINE_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("General.noPlayerOnline"));
    }

    public static String GET_NO_PLAYER_FOUND_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("General.noPlayerFound"));
    }

    public static String GET_STAFF_CHAT_FORMAT() {
        return TextUtils.parseMessage(messages.getString("StaffChat.format"));
    }

    public static String GET_RELOADED_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Reloaded"));
    }

    /*
    * Freeze Messages
    */

    public static String GET_FREEZE_FROZEN_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.frozen"));
    }

    public static String GET_FREEZE_UNFROZEN_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.unfrozen"));
    }


    public static String GET_FREEZE_FROZEN_BY_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.frozenByOther"));
    }

    public static String GET_FREEZE_UNFROZEN_BY_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.unfrozenBy"));
    }

    public static ArrayList<String> spamMessage() {
        ArrayList<String> spam = new ArrayList<String>();
        for (String s : messages.getStringList("Freeze.playerSpamMessage")) {
            spam.add(TextUtils.parseMessage(s));
        }
        return spam;
    }


    /*
    * General
    */
    public static String GET_NO_PERMISSION_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("NoPermission"));
    }

    public static String PREFIX(){
        return messages.getString("Prefix");
    }

    public static void reload(){
        messages = ConfigLoader.getMessages().getConfig();
    }
}
