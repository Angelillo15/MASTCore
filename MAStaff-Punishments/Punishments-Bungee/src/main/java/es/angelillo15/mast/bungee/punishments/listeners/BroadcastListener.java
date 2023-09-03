package es.angelillo15.mast.bungee.punishments.listeners;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.punishments.Config;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.event.bungee.ban.BannedPlayerTriesToJoin;
import es.angelillo15.mast.api.event.bungee.ban.PlayerBannedEvent;
import es.angelillo15.mast.api.models.BansTable;
import es.angelillo15.mast.api.models.IpBansTable;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BroadcastListener implements Listener {
    @EventHandler
    public void onPlayerAttempt(BannedPlayerTriesToJoin event) {
        BansTable ban = event.getBanModel();

        if (ban.isPermanent()) {
            sendBroadcast(Messages.Ban.bannedPlayerTriesToJoin(
                    event.getPlayer(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBanned_by_name(),
                    ban.getReason()
            ), "mast.punishments.ban.notify.join");
        } else {
            sendBroadcast(Messages.Ban.tempBannedPlayerTriesToJoin(
                    event.getPlayer(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBanned_by_name(),
                    ban.getReason(),
                    TextUtils.formatUptime(ban.getUntil() - System.currentTimeMillis()),
                    TextUtils.formatDate(ban.getUntil(), Config.dateFormat())
            ), "mast.punishments.ban.notify.join");
        }
    }

    @EventHandler
    public void onPlayerBanned(PlayerBannedEvent event) {
        BansTable ban = event.getBanModel();

        if (ban.isPermanent()) {
            sendBroadcast(Messages.Ban.bannedBroadcast(
                    ban.getUsername(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBanned_by_name(),
                    ban.getReason()
            ), "mast.punishments.ban.notify");
        } else {
            sendBroadcast(Messages.Ban.tempBannedBroadcast(
                    ban.getUsername(),
                    TextUtils.formatDate(ban.getTime(), Config.dateFormat()),
                    ban.getBanned_by_name(),
                    ban.getReason(),
                    TextUtils.formatUptime(ban.getUntil() - System.currentTimeMillis()),
                    TextUtils.formatDate(ban.getUntil(), Config.dateFormat())
            ), "mast.punishments.ban.notify");
        }

        MAStaffInstance.getLogger().debug("PlayerBannedEvent triggered");
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(event.getBanModel().getUsername());

        if (player != null) {
            player.disconnect(PlayerBanListener.getBaseComponent(event.getBanModel(), player.getName()));
        }
        MAStaffInstance.getLogger().debug("event.getBanModel().getIpban() = " + event.getBanModel().getIpban());
        if (event.getBanModel().getIpban()) {
            IpBansTable ipban = IpBansTable.getIpBanned(event.getBanModel().getId());
            MAStaffInstance.getLogger().debug("IpBansTable.getIpBanned(" + event.getBanModel().getId() + ") returned " + ipban);
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.getAddress().getAddress().getHostAddress().equals(ipban.getIp())) {
                    p.disconnect(PlayerBanListener.getBaseComponent(event.getBanModel(), p.getName()));
                }
            }
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
