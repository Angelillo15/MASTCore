package es.angelillo15.mast.api.punishments;

import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.config.punishments.Messages;

public interface IPunishPlayer {
    /**
     * Get the player
     * @return the player
     */
    CommandSender getPlayer();

    /**
     * Get the uuid of the player
     * @return the uuid of the player
     */
    String getUUID();

    /**
     * Get the name of the player
     * @return the name of the player
     */
    String getName();

    /**
     * Ban the player forever with the default reason
     */
    default void ban(String target){
        ban(target, false);
    }

    /**
     * Ban the player forever with the default reason
     * @param ipban if the ban should be an ipban
     */
    default void ban(String target, boolean ipban){
        ban(target, Messages.Default.defaultBanReason(), ipban);
    }

    /**
     * Ban the player forever with a custom reason
     * @param reason the reason of the ban
     */
    default void ban(String target, String reason){
        ban(target, reason, false);
    }

    /**
     * Ban the player forever with a custom reason
     * @param reason the reason of the ban
     * @param ipban if the ban should be an ipban
     */
    default void ban(String target, String reason, boolean ipban){
        ban(target, reason, 0, ipban);
    }

    /**
     * Ban the player for a certain amount of time with the default reason
     * @param reason the reason of the ban
     * @param until the time in milliseconds when the ban will expire
     */
    default void ban(String target, String reason, long until) {
        ban(target, reason, until, false);
    }

    /**
     * Ban the player for a certain amount of time with the default reason
     * @param target the target of the ban
     * @param reason the reason of the ban
     * @param until the time in milliseconds when the ban will expire
     * @param ipban if the ban should be an ipban
     */
    void ban(String target, String reason, long until, boolean ipban);

    /**
     * Unban the player with the default reason
     * @param target the target of the unban
     */
    default void unban(String target) {
        unban(target, Messages.Default.defaultUnbanReason());
    }

    /**
     * Unban the player with a custom reason
     * @param target the target of the unban
     * @param reason the reason of the unban
     */
    void unban(String target, String reason);

    /**
     * Kick the player with the default reason
     * @param target the target of the kick
     */
    default void kick(String target) {
        kick(target, Messages.Default.defaultKickReason());
    }

    /**
     * Kick the player with a custom reason
     * @param target the target of the kick
     * @param reason the reason of the kick
     */
    void kick(String target, String reason);

}
