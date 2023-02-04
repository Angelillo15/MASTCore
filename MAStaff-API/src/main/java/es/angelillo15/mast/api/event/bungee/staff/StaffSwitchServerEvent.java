package es.angelillo15.mast.api.event.bungee.staff;

import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class StaffSwitchServerEvent extends Event {
    @Getter
    private ProxiedPlayer player;
    @Getter
    private String previousServer;
    @Getter
    private String newServer;

    public StaffSwitchServerEvent(ProxiedPlayer player, String previousServer, String newServer){
        this.player = player;
        this.previousServer = previousServer;
        this.newServer = newServer;
    }
}
