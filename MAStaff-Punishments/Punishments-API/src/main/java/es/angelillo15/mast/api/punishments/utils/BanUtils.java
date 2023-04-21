package es.angelillo15.mast.api.punishments.utils;

import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.punishments.data.DataManager;

public class BanUtils {
    public static void ban(String uuid, String ip, String reason, String banned_by_uuid, String banned_by_name, boolean active, long time, long until, boolean ipban) {
        DataManager.getDataManager().ban(uuid, ip, reason, banned_by_uuid, banned_by_name, active, time, until, ipban);
    }

    public static void ban(CommandSender banned_by, CommandSender target, String reason, long until, boolean ipban) {
        ban(target.getUniqueId(), target.getAddress(), reason, banned_by.getUniqueId(), banned_by.getName(), true, System.currentTimeMillis(), until, ipban);
    }

    public static void permBan(CommandSender banned_by, CommandSender target, String reason, boolean ipban) {
        ban(banned_by, target, reason, 0, ipban);
    }
}
