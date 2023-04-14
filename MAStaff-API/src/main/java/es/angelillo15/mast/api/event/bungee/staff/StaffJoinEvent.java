package es.angelillo15.mast.api.event.bungee.staff;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class StaffJoinEvent extends Event {
    private ProxiedPlayer player;

    public StaffJoinEvent(ProxiedPlayer player){
        this.player = player;
    }

    /**
     * Get the player of the event
     *
     * @return The player
     */
    public ProxiedPlayer getPlayer(){
        return player;
    }
}
