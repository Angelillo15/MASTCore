package es.angelillo15.mast.api.config.punishments;

import es.angelillo15.mast.api.TextUtils;

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
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultUnBanReason");
        }

        public static String defaultUnmuteReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultUnMuteReason");
        }

        public static String defaultWarnReason() {
            return ConfigLoader.getMessages().getConfig().getString("Default.defaultWarnReason");
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

        public static String ipBannedMessage() {
            return formatBase(ConfigLoader.getMessages().getConfig().getString("Ban.ipBannedMessage"));
        }

        public static String ipBannedMessagePermanent() {
            return formatBase(ConfigLoader.getMessages().getConfig().getString("Ban.ipBannedMessagePermanent"));
        }

        public static String formatBase(String msg) {
            return colorize(msg)
                    .replace("{base}", colorize(banMessageBase()))
                    .replace("{appeal}", colorize(Default.appealMessage())
                    );
        }

        public static String bannedPlayerTriesToJoin() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Ban.bannedPlayerTriesToJoin"));
        }

        public static String bannedPlayerTriesToJoin(String player, String bannedOn, String bannedBy, String reason) {
            return colorize(bannedPlayerTriesToJoin())
                    .replace("{player}", player)
                    .replace("{bannedOn}", bannedOn)
                    .replace("{bannedBy}", bannedBy)
                    .replace("{reason}", reason);
        }

        public static String tempBannedPlayerTriesToJoin() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Ban.tempBannedPlayerTriesToJoin"));
        }

        public static String tempBannedPlayerTriesToJoin(String player, String bannedOn, String bannedBy, String reason, String duration, String expires) {
            return colorize(tempBannedPlayerTriesToJoin())
                    .replace("{player}", player)
                    .replace("{bannedOn}", bannedOn)
                    .replace("{bannedBy}", bannedBy)
                    .replace("{reason}", reason)
                    .replace("{duration}", duration)
                    .replace("{expires}", expires);
        }

        public static String bannedBroadcast() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Ban.bannedBroadcast"));
        }

        public static String bannedBroadcast(String player, String bannedOn, String bannedBy, String reason) {
            return colorize(bannedBroadcast())
                    .replace("{player}", player)
                    .replace("{bannedOn}", bannedOn)
                    .replace("{bannedBy}", bannedBy)
                    .replace("{reason}", reason);
        }

        public static String tempBannedBroadcast() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Ban.tempBannedBroadcast"));
        }

        public static String tempBannedBroadcast(String player, String bannedOn, String bannedBy, String reason, String duration, String expires) {
            return colorize(tempBannedBroadcast())
                    .replace("{player}", player)
                    .replace("{bannedOn}", bannedOn)
                    .replace("{bannedBy}", bannedBy)
                    .replace("{reason}", reason)
                    .replace("{duration}", duration)
                    .replace("{expires}", expires);
        }
    }

    public static class Kick {
        public static String kickMessage() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Kick.kickedMessage"));
        }

        public static String kickMessage(String player, String kickedBy, String reason) {
            return colorize(kickMessage())
                    .replace("{player}", player)
                    .replace("{kickedBy}", kickedBy)
                    .replace("{reason}", reason);
        }

        public static String kickBroadcast() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Kick.kickedBroadcast"));
        }

        public static String kickBroadcast(String player, String kickedBy, String reason) {
            return colorize(kickBroadcast())
                    .replace("{player}", player)
                    .replace("{kickedBy}", kickedBy)
                    .replace("{reason}", reason);
        }
    }

    public static class Warn {
        public static boolean titleOnWarn() {
            return ConfigLoader.getMessages().getConfig().getBoolean("Warn.titleOnWarn");
        }

        public static String title() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Warn.title"));
        }

        public static String subtitle() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Warn.subtitle"));
        }

        public static String warnedMessage() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Warn.warnedMessage"));
        }

        public static String warnedMessage(String player, String warnedBy, String reason) {
            return colorize(warnedMessage())
                    .replace("{player}", player)
                    .replace("{warnedBy}", warnedBy)
                    .replace("{reason}", reason);
        }

        public static String warnedBroadcast() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Warn.warnedBroadcast"));
        }

        public static String warnedBroadcast(String player, String warnedBy, String reason) {
            return colorize(warnedBroadcast())
                    .replace("{player}", player)
                    .replace("{warnedBy}", warnedBy)
                    .replace("{reason}", reason);
        }
    }

    public static class Commands {
        public static String playerNotFound() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.playerNotFound"));
        }

        public static String playerNotFound(String player) {
            return colorize(playerNotFound())
                    .replace("{player}", player);
        }

        public static String playerNotBanned() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.playerNotBanned"));
        }

        public static String playerNotBanned(String player) {
            return colorize(playerNotBanned())
                    .replace("{player}", player);
        }

        public static String playerNotMuted() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.playerNotMuted"));
        }

        public static String playerNotMuted(String player) {
            return colorize(playerNotMuted())
                    .replace("{player}", player);
        }

        public static String playerNotWarned() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.playerNotWarned"));
        }

        public static String playerNotWarned(String player) {
            return colorize(playerNotWarned())
                    .replace("{player}", player);
        }

        public static String playerNotOnline() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.playerNotOnline"));
        }

        public static String playerNotOnline(String player) {
            return colorize(playerNotOnline())
                    .replace("{player}", player);
        }

        public static String playerAlreadyBanned() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.playerAlreadyBanned"));
        }

        public static String playerAlreadyBanned(String player) {
            return colorize(playerAlreadyBanned())
                    .replace("{player}", player);
        }

        public static String playerAlreadyMuted() {
            return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.playerAlreadyMuted"));
        }

        public static String playerAlreadyMuted(String player) {
            return colorize(playerAlreadyMuted())
                    .replace("{player}", player);
        }

        public static class Ban {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.ban.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.ban.success"));
            }

            public static String success(String player, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{reason}", reason)
                        .replace("{staff}", staff);
            }
        }

        public static class TempBan {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.tempBan.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.tempBan.success"));
            }

            public static String success(String player, String time, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{duration}", time)
                        .replace("{staff}", staff)
                        .replace("{reason}", reason);

            }
        }

        public static class IpBan {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.ipBan.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.ipBan.success"));
            }

            public static String success(String player, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{reason}", reason)
                        .replace("{staff}", staff);
            }
        }

        public static class TempIpBan {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.tempIpBan.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.tempIpBan.success"));
            }

            public static String success(String player, String time, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{duration}", time)
                        .replace("{staff}", staff)
                        .replace("{reason}", reason);

            }
        }

        public static class Unban {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.unban.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.unban.success"));
            }

            public static String success(String player, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{reason}", reason)
                        .replace("{staff}", staff);
            }
        }

        public static class Kick {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.kick.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.kick.success"));
            }

            public static String success(String player, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{reason}", reason)
                        .replace("{staff}", staff);
            }
        }

        public static class Warn {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.warn.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.warn.success"));
            }

            public static String success(String player, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{reason}", reason)
                        .replace("{staff}", staff);
            }
        }

        public static class UnWarn {
            public static String usage() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.unwarn.usage"));
            }

            public static String success() {
                return colorize(ConfigLoader.getMessages().getConfig().getString("Commands.unwarn.success"));
            }

            public static String success(String player, String reason, String staff) {
                return colorize(success())
                        .replace("{player}", player)
                        .replace("{reason}", reason)
                        .replace("{staff}", staff);
            }
        }
    }

    public static String colorize(String message) {
        return TextUtils.colorize(message)
                .replace("{prefix}", Default.prefix()
                ).replace("&", "§");
    }
}
