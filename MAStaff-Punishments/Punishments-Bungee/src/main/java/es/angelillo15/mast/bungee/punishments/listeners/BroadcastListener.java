package es.angelillo15.mast.bungee.punishments.listeners;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.event.bungee.ban.BannedPlayerTriesToJoin;
import es.angelillo15.mast.api.event.bungee.ban.PlayerBannedEvent;
import es.angelillo15.mast.api.models.BanModel;
import es.angelillo15.mast.api.punishments.config.Config;
import es.angelillo15.mast.api.punishments.config.Messages;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BroadcastListener implements Listener {
    @EventHandler
    public void onPlayerAttempt(BannedPlayerTriesToJoin event) {
        BanModel ban = event.getBanModel();

        if (ban.isPermanent()) {
            sendBroadcast(Messages.Ban.bannedPlayerTriesToJoin(
                    event.getPlayer(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBannedBy(),
                    ban.getReason()
            ), "mast.punishments.ban.notify.join");
        } else {
            sendBroadcast(Messages.Ban.tempBannedPlayerTriesToJoin(
                    event.getPlayer(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBannedBy(),
                    ban.getReason(),
                    TextUtils.formatUptime(ban.getUntil() - System.currentTimeMillis()),
                    TextUtils.formatDate(ban.getUntil(), Config.dateFormat())
            ), "mast.punishments.ban.notify.join");
        }
    }

    @EventHandler
    public void onPlayerBanned(PlayerBannedEvent event) {
        BanModel ban = event.getBanModel();

        if (ban.isPermanent()) {
            sendBroadcast(Messages.Ban.bannedBroadcast(
                    ban.getUsername(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBannedBy(),
                    ban.getReason()
            ), "mast.punishments.ban.notify");
        } else {
            sendBroadcast(Messages.Ban.tempBannedBroadcast(
                    ban.getUsername(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBannedBy(),
                    ban.getReason(),
                    TextUtils.formatUptime(ban.getUntil() - System.currentTimeMillis()),
                    TextUtils.formatDate(ban.getUntil(), Config.dateFormat())
            ), "mast.punishments.ban.notify");
        }
    }

    public void sendBroadcast(String msg, String permission) {
        new Thread(() -> {
            MAStaffInstance.getLogger().info(msg);
            ProxyServer.getInstance().getPlayers().forEach((ProxiedPlayer player) -> {
                if (player.hasPermission(permission)) {
                    player.sendMessage(new TextComponent(msg));
                }
            });
        }).start();
    }
}
