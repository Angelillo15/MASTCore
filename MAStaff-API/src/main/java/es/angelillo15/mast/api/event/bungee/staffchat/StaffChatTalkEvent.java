package es.angelillo15.mast.api.event.bungee.staffchat;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class StaffChatTalkEvent extends Event {
    private ProxiedPlayer player;
    private String message;

    public StaffChatTalkEvent(ProxiedPlayer player, String message){
        this.player = player;
        this.message = message;
    }

    /**
     * Get the player of the event
     *
     * @return The player
     */
    public ProxiedPlayer getPlayer(){
        return player;
    }

    /**
     * Get the message of the event
     * @return The message
     */
    public String getMessage(){
        return message;
    }
}
