package es.angelillo15.mast.api.config.velocity;

import com.google.inject.Inject;
import es.angelillo15.mast.api.TextUtils;

public class Messages {
    @Inject
    private static VelocityConfig config;

    public static String getString(String path) {
        return TextUtils.colorize(
                config.getMessages().getConfig().getString(path)
                        .replace("{prefix}", config.getMessages().getConfig().getString("Messages.prefix"))
        );
    }

    public static String reloaded() {
        return getString("Reloaded");
    }

    public static String prefix() {
        return getString("Messages.prefix");
    }

    public static String playerStaffModeEnabled() {
        return getString("Messages.playerStaffModeEnabled");
    }

    public static String playerStaffModeDisabled() {
        return getString("Messages.playerStaffModeDisabled");
    }
}
