package es.angelillo15.mast.api.managers;

import java.util.ArrayList;

@Deprecated
public class LegacyStaffChatManager {
    private static final ArrayList<String> staffChatEnable = new ArrayList<>();

    public static void setStaffChatEnable(String playerUUID, boolean staffChat) {
        if (staffChat) {
            staffChatEnable.add(playerUUID);
        } else {
            staffChatEnable.remove(playerUUID);
        }
    }

    public static boolean isStaffChatEnable(String playerUUID) {
        return staffChatEnable.contains(playerUUID);
    }

    public static void removePlayer(String playerUUID) {
        staffChatEnable.remove(playerUUID);
    }

    public static void clear() {
        staffChatEnable.clear();
    }
}