package es.angelillo15.mast.bungee.punishments.listeners;

import com.google.inject.Inject;
import es.angelillo15.mast.api.cmd.sender.ProxiedPlayerCommandSender;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;
import es.angelillo15.mast.api.utils.MAStaffInject;
import es.angelillo15.mast.api.punishments.PunishPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PunishPlayerListener implements Listener {
    @Inject
    private MAStaffInject inject;

    @EventHandler
    public void onPlayerPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (!(player.hasPermission("mast.punishments"))) {
            return;
        }

        PunishPlayersManager.addPlayer(
                inject.getInjector().getInstance(PunishPlayer.class).setPlayer(new ProxiedPlayerCommandSender(player))
        );
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (!(player.hasPermission("mast.punishments"))) {
            return;
        }

        PunishPlayersManager.removePlayer(player.getUniqueId().toString());
    }
}
