package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.config.ConfigManager;
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

    public static String GET_VANISH_ENABLE_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Vanish.enabled"));
    }

    public static String GET_VANISH_DISABLE_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Vanish.disabled"));
    }

    /*
    * Freeze Messages
    */

    public static String GET_FREEZE_CORRECT_USE_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.correctUse"));
    }
    public static String GET_FREEZE_FROZEN_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.frozen"));
    }

    public static String GET_FREEZE_UNFROZEN_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.unfrozen"));
    }

    public static String GET_FREEZE_FROZEN_OTHER_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.frozenBy"));
    }

    public static String GET_FREEZE_UNFROZEN_OTHER_MESSAGE() {
        return TextUtils.parseMessage(messages.getString("Freeze.unfrozenBy"));
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
}
