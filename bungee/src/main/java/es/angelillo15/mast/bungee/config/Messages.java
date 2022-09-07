package es.angelillo15.mast.bungee.config;

import es.angelillo15.mast.config.ConfigManager;

public class Messages {
    private static ConfigManager messages = ConfigLoader.getMessages();

    public static String getPrefix() {
        return messages.getConfig().getString("Messages.prefix");
    }

    public static String getPlayerStaffModeEnabled() {
        return messages.getConfig().getString("Messages.playerStaffModeEnabled");
    }

    public static String getPlayerStaffModeDisabled() {
        return messages.getConfig().getString("Messages.playerStaffModeDisabled");
    }
}
