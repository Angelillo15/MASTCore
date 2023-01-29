package es.angelillo15.mast.api.event.bukkit.staff;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player sends a message to the staffchat
 * @author Angelillo15
 * @since 2.1.0
 */
public class StaffChatTalkEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private String message;
    private Player sender;

    public StaffChatTalkEvent(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Get the staffchat message
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the player who sent the message
     * @return Player
     */
    public Player getSender() {
        return sender;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
