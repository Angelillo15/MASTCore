package es.angelillo15.mast.api.punishments.events;

import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.event.bungee.ban.BannedPlayerTriesToJoin;
import es.angelillo15.mast.api.event.bungee.ban.PlayerBannedEvent;
import es.angelillo15.mast.api.models.BanModel;
import net.md_5.bungee.api.ProxyServer;

public class BungeeEventManager extends EventManager {
    @Override
    public void sendPlayerBannedEvent(BanModel banModel, CommandSender sender) {
        PlayerBannedEvent event = new PlayerBannedEvent(banModel, sender, (result, error) -> {
            if (error != null) {
                error.printStackTrace();
            }
        });

        ProxyServer.getInstance().getPluginManager().callEvent(event);
    }

    @Override
    public void sendPlayerTryToJoinBannedEvent(BanModel banModel, String player) {
        BannedPlayerTriesToJoin event = new BannedPlayerTriesToJoin(banModel, player);

        ProxyServer.getInstance().getPluginManager().callEvent(event);
    }
}
