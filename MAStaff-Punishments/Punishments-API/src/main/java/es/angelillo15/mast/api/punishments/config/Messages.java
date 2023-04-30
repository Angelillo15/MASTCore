package es.angelillo15.mast.api.punishments.config;

import es.angelillo15.mast.api.TextUtils;

import java.util.List;

public class Messages {
    public static class Default {
        public static String prefix() {
            return ConfigLoader.getMessages().getConfig().getString("Default.prefix");
        }
        public static String defaultBanReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultBanReason");
        }

        public static String defaultKickReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultKickReason");
        }

        public static String defaultMuteReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultMuteReason");
        }

        public static String defaultUnbanReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultUnbanReason");
        }

        public static String defaultUnmuteReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultUnMuteReason");
        }

        public static String defaultUnWarnReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultUnWarnReason");
        }

        public static String appealMessage() {
            return ConfigLoader.getMessages().getConfig().getString("Default.appealMessage");
        }
    }

    public static class Ban {
        public static String banMessageBase() {
            return ConfigLoader.getMessages().getConfig().getString("Ban.bannedMessageBase");
        }

        public static String tempBanMessageBase() {
            return formatBase(ConfigLoader.getMessages().getConfig().getString("Ban.bannedMessage"));
        }

        public static String bannedMessagePermanent() {
            return formatBase(ConfigLoader.getMessages().getConfig().getString("Ban.bannedMessagePermanent"));
        }

        public static String formatBase(String msg) {
            return colorize(msg)
                    .replace("{base}", colorize(banMessageBase()))
                    .replace("{appeal}", colorize(Default.appealMessage())
                    );
        }
    }

    public static String colorize(String message) {
        return TextUtils.colorize(message)
                .replace("{prefix}", Default.prefix()
                );
    }


}
