package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.config.ConfigManager;
import org.simpleyaml.configuration.file.YamlFile;

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


    public static String PREFIX(){
        return messages.getString("Prefix");
    }
}
