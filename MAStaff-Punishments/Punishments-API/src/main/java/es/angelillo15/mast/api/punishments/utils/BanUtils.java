package es.angelillo15.mast.api.punishments.utils;

import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.models.UserModel;
import es.angelillo15.mast.api.punishments.enums.ErrorTypes;

public class BanUtils {
    public static void ban(String uuid, String username, String ip, String reason, String banned_by_uuid, String banned_by_name, boolean active, long time, long until, boolean ipban) {
    }

    public static ErrorTypes ban(CommandSender banned_by, UserModel data, String reason, long until, boolean ipban) {
          return null;
    }

    public static boolean isBanned(String username) {
        return false;
    }

    public static ErrorTypes permBan(CommandSender banned_by, UserModel target, String reason, boolean ipban) {
        return null;
    }

    public static UserModel getUserData(String username) {
        return null;
    }

    public static void unban(String username, String reason) {

    }

    public static void unban(String username) {

    }
}
