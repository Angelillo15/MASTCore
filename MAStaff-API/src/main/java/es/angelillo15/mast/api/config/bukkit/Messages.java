package es.angelillo15.mast.api.config.bukkit;

import es.angelillo15.mast.api.TextUtils;
import lombok.Setter;
import org.simpleyaml.configuration.file.YamlFile;
import java.util.ArrayList;
import java.util.List;

public class Messages {
    @Setter
    private static YamlFile messages = ConfigLoader.getMessages().getConfig();

    public static String GET_STAFF_MODE_ENABLE_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("StaffMode.toggledOn"));
    }

    public static String GET_STAFF_MODE_DISABLE_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("StaffMode.toggledOff"));
    }

    public static String GET_NO_PLAYER_ONLINE_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("General.noPlayerOnline"));
    }

    public static String GET_NO_PLAYER_FOUND_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("General.noPlayerFound"));
    }

    public static String GET_STAFF_CHAT_FORMAT() {
        return TextUtils.parseBukkitMessage(messages.getString("StaffChat.format"));
    }

    public static String GET_RELOADED_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Reloaded"));
    }

    /**
     * Get the message when a player is vanished
     * @return The message
     */

    public static String GET_VANISH_JOIN_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Vanish.joinMessage"));
    }

    public static String GET_VANISH_LEAVE_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Vanish.leaveMessage"));
    }

    public static String GET_STAFF_VANISH_JOIN_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Vanish.staffJoinMessage"));
    }

    public static String GET_STAFF_VANISH_LEAVE_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Vanish.staffLeaveMessage"));
    }

    public static String GET_VANISH_ENABLE_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Vanish.enable"));
    }

    public static String GET_VANISH_DISABLE_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Vanish.disable"));
    }

    public static String GET_FREEZE_FROZEN_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Freeze.frozen"));
    }

    public static String GET_FREEZE_UNFROZEN_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Freeze.unfrozen"));
    }


    public static String GET_FREEZE_FROZEN_BY_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Freeze.frozenByOther"));
    }

    public static String GET_FREEZE_UNFROZEN_BY_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Freeze.unfrozenBy"));
    }

    public static ArrayList<String> spamMessage() {
        ArrayList<String> spam = new ArrayList<String>();
        for (String s : messages.getStringList("Freeze.playerSpamMessage")) {
            spam.add(TextUtils.parseBukkitMessage(s));
        }
        return spam;
    }

    /*
     * StaffChat Messages
     */

    public static String GET_STAFFCHAT_CORRECT_USE(){
        return TextUtils.parseBukkitMessage(messages.getString("StaffChat.correctUse"));
    }



    /*
     * General
     */
    public static String GET_NO_PERMISSION_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("NoPermission"));
    }

    /*
     * StaffList
     */
    public static String GET_STAFFLIST_TITLE() {
        return TextUtils.parseBukkitMessage(messages.getString("StaffList.title"));
    }

    public static List<String> GET_STAFFLIST_LORE() {
        return messages.getStringList("StaffList.staffLore");
    }

    public static String GET_STAFFLIST_PREVIUS(){
        return TextUtils.parseBukkitMessage(messages.getString("StaffList.previousPage"));
    }

    public static String GET_STAFFLIST_NEXT(){
        return TextUtils.parseBukkitMessage(messages.getString("StaffList.nextPage"));
    }

    public static String PREFIX(){
        return messages.getString("Prefix");
    }


    /*
     * Gui
     */
    public static String GET_GUI_TITLE(){
        return TextUtils.parseBukkitMessage(ConfigLoader.getPunishmentsGUI().getConfig().getString("Gui.title"));
    }

    public static class StaffVault {
        public static String itemSaved() {
            return TextUtils.parseBukkitMessage(messages.getString("StaffVault.itemSaved"));
        }

        public static String staffVaultIsFull() {
            return TextUtils.parseBukkitMessage(messages.getString("StaffVault.staffVaultFull"));
        }

        public static String staffVaultInStaffMode() {
            return TextUtils.parseBukkitMessage(messages.getString("StaffVault.staffVaultInStaffMode"));
        }
    }

    /*
     * Freeze
     */
    public static String CONFIRM_PUNISH_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Freeze.confirmPunishMessage"));
    }

    public static String GET_FREEZE_CANNOT_FREEZE_THAT_PLAYER_MESSAGE() {
        return TextUtils.parseBukkitMessage(messages.getString("Freeze.freezeBypassMessage"));
    }

}
