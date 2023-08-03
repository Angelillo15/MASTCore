package es.angelillo15.mast.api.punishments;

import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.templates.BanTemplate;

public interface IPunishPlayer {
    /**
     * Get the player
     *
     * @return the player
     */
    CommandSender getPlayer();

    /**
     * Get the uuid of the player
     *
     * @return the uuid of the player
     */
    String getUUID();

    /**
     * Get the name of the player
     *
     * @return the name of the player
     */
    String getName();

    /**
     * Send a message to the player
     *
     * @param message the message to send
     */
    default void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    /**
     * Ban the player with a template ban
     *
     * @param target   the target of the ban
     * @param template the template of the ban
     */
    default void ban(String target, BanTemplate template) {
        if (template.getBanDuration() == 0) {
            ban(target, template.getBanMessage(), template.getIpBan());
        } else {
            ban(target, template.getBanMessage(), System.currentTimeMillis() + template.getBanDuration(), template.getIpBan());
        }
    }

    /**
     * Ban the player forever with the default reason
     */
    default void ban(String target) {
        ban(target, false);
    }

    /**
     * Ban the player forever with the default reason
     *
     * @param ipban if the ban should be an ipban
     */
    default void ban(String target, boolean ipban) {
        ban(target, Messages.Default.defaultBanReason(), ipban);
    }

    /**
     * Ban the player forever with a custom reason
     *
     * @param reason the reason of the ban
     */
    default void ban(String target, String reason) {
        ban(target, reason, false);
    }

    /**
     * Ban the player forever with a custom reason
     *
     * @param reason the reason of the ban
     * @param ipban  if the ban should be an ipban
     */
    default void ban(String target, String reason, boolean ipban) {
        ban(target, reason, 0, ipban);
    }

    /**
     * Ban the player for a certain amount of time with the default reason
     *
     * @param reason the reason of the ban
     * @param until  the time in milliseconds when the ban will expire
     */
    default void ban(String target, String reason, long until) {
        ban(target, reason, until, false);
    }

    /**
     * Ban the player for a certain amount of time with the default reason
     *
     * @param target the target of the ban
     * @param reason the reason of the ban
     * @param until  the time in milliseconds when the ban will expire
     * @param ipban  if the ban should be an ipban
     */
    void ban(String target, String reason, long until, boolean ipban);

    /**
     * Unban the player with the default reason
     *
     * @param target the target of the unbanning
     */
    default void unban(String target) {
        unban(target, Messages.Default.defaultUnbanReason());
    }

    /**
     * Unban the player with a custom reason
     *
     * @param target the target of the unbanning
     * @param reason the reason of the unbanning
     */
    void unban(String target, String reason);

    /**
     * Kick the player with the default reason
     *
     * @param target the target of the kick
     */
    default void kick(String target) {
        kick(target, Messages.Default.defaultKickReason());
    }

    /**
     * Kick the player with a custom reason
     *
     * @param target the target of the kick
     * @param reason the reason of the kick
     */
    void kick(String target, String reason);

    /**
     * Warn the player with the config default time and reason
     *
     * @param target the target of the warning
     */
    default void warn(String target) {
        warn(target, Messages.Default.defaultWarnReason(), "");
    }

    /**
     * Warn the player with the config default time and a custom reason
     *
     * @param target the target of the warning
     * @param reason the reason of the warning
     */
    default void warn(String target, String reason) {
        warn(target, reason, "");
    }

    /**
     * Warn the player with the config default time and a custom reason
     *
     * @param target the target of the warning
     * @param reason the reason of the warning
     */
    void warn(String target, String reason, String template);

    /**
     * UnWarn the player
     *
     * @param target the target of the unwarn
     */
    default void unWarn(String target) {
        unWarn(target, Messages.Default.defaultUnWarnReason());
    }

    /**
     * UnWarn the player
     *
     * @param target the target of the unwarn
     * @param reason the reason of the unwarn
     */
    void unWarn(String target, String reason);

    boolean tryBanTemplate(String target, String template);

}
