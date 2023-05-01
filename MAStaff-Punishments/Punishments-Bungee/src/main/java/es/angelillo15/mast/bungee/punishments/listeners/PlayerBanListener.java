package es.angelillo15.mast.bungee.punishments.listeners;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.event.bungee.ban.PlayerBannedEvent;
import es.angelillo15.mast.api.models.BanModel;
import es.angelillo15.mast.api.models.IPBanModel;
import es.angelillo15.mast.api.punishments.cache.BanCache;
import es.angelillo15.mast.api.punishments.config.Config;
import es.angelillo15.mast.api.punishments.config.Messages;
import es.angelillo15.mast.api.punishments.data.AbstractDataManager;
import es.angelillo15.mast.api.punishments.events.EventManager;
import es.angelillo15.mast.api.punishments.utils.BanUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.UUID;

public class PlayerBanListener implements Listener {
    @EventHandler
    public void onJoin(PreLoginEvent event) {
        PendingConnection conn = event.getConnection();
        event.registerIntent((Plugin) MAStaffInstance.getBungeeInstance());

        if (cacheCheck(conn)) {
            event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
            MAStaffInstance.getLogger().debug("Cache check");
            return;
        }
        if (ipBanCheck(conn)) {
            event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
            MAStaffInstance.getLogger().debug("IP ban check");
            return;
        }

        if (databaseCheck(conn)) {
            MAStaffInstance.getLogger().debug("Database check");
            event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
        }

        try {
            event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
        } catch (Exception ignored) {

        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPostLogin(PostLoginEvent event) {
        /*
        new Thread(() -> {
            MAStaffInstance.getLogger().debug("Post login event");
            newUser(event.getPlayer());

            ProxiedPlayer player = event.getPlayer();

            AbstractDataManager manager =
                    es.angelillo15.mast.api.punishments.data.DataManager.getDataManager();

            if (manager.isBanned(player.getName())) {
                manager.setUUID(player.getName(), player.getUniqueId());
                manager.setUsername(player.getUniqueId(), player.getName());
            }
        }).start();
         */
    }

    @EventHandler
    public void onPlayerBan(PlayerBannedEvent event) {
        event.registerIntent((Plugin) MAStaffInstance.getBungeeInstance());

        ProxiedPlayer banned = ProxyServer.getInstance().getPlayer(event.getBanModel().getUuid());
        AbstractDataManager manager =
                es.angelillo15.mast.api.punishments.data.DataManager.getDataManager();

        if (event.getBanModel().isIpBan()) {
            IPBanModel ipBanModel = manager.getIPBan(event.getBanModel().getId());

            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getAddress().getAddress().getHostAddress().split(":")[0].equals(ipBanModel.getIp())) {
                    player.disconnect(getBaseComponent(event.getBanModel()));
                }
            }
        }

        if (banned != null) {
            banned.disconnect(getBaseComponent(event.getBanModel()));
        }

        event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
    }

    private boolean cacheCheck(PendingConnection conn) {
        MAStaffInstance.getLogger().debug("Checking cache for " + conn.getName() + "'s ban");

        if (!BanCache.isPunished(conn.getName())) {
            MAStaffInstance.getLogger().debug("Not in cache");
            return false;
        }

        BanModel model = BanCache.getPunishment(conn.getName());

        if (model != null) {
            disconnect(model, conn);
            EventManager.getEventManager().sendPlayerTryToJoinBannedEvent(model, conn.getName());
            return true;
        }
        return false;
    }

    private boolean ipBanCheck(PendingConnection connection) {
        String ip = connection.getAddress().getAddress().getHostAddress().split(":")[0];

        MAStaffInstance.getLogger().debug("Checking cache for " + ip + "'s ban");

        if (BanCache.isPunished(ip)) {
            disconnect(BanCache.getPunishment(ip), connection);
            MAStaffInstance.getLogger().debug("Found in cache");
            return true;
        }

        AbstractDataManager manager =
                es.angelillo15.mast.api.punishments.data.DataManager.getDataManager();

        if (!manager.isIPBanned(ip)) {
            MAStaffInstance.getLogger().debug("Not in cache but not banned");
            return false;
        }

        MAStaffInstance.getLogger().debug("Not in cache but banned");

        IPBanModel model = manager.getIPBan(ip);
        BanModel banModel = manager.getBan(model.getBan_id(), true);

        if (banModel != null) {
            if (banModel.isExpired()) {
                BanUtils.unban(banModel.getUsername());
                return false;
            }

            disconnect(banModel, connection);
            EventManager.getEventManager().sendPlayerTryToJoinBannedEvent(banModel, connection.getName());
            BanCache.addPunishment(ip, banModel);
            return true;
        }

        return false;
    }

    public void newUser(ProxiedPlayer conn) {
        MAStaffInstance.getLogger().debug("Registering " + conn.getName() + " in database");

        AbstractDataManager manager =
                es.angelillo15.mast.api.punishments.data.DataManager.getDataManager();

        if (!manager.isBanned(conn.getName())) return;

        BanModel model = manager.getBan(conn.getName());
        disconnect(model, conn.getPendingConnection());
        BanCache.addPunishment(conn.getName(), model);
        EventManager.getEventManager().sendPlayerTryToJoinBannedEvent(model, conn.getName());
    }


    private boolean databaseCheck(PendingConnection player) {
        MAStaffInstance.getLogger().debug("Checking database for " + player.getName() + "'s ban");

        UserData userData = null;

        userData = DataManager.getDataManager().getUserData(player.getName());

        AbstractDataManager manager =
                es.angelillo15.mast.api.punishments.data.DataManager.getDataManager();

        if (userData == null) {
            return false;
        }
        BanModel model = manager.getBan(UUID.fromString(userData.getUUID()));

        if (manager.isPermBanned(userData.getUUID())) {

            disconnect(model, player);

            BanCache.addPunishment(player.getName(), model);
            EventManager.getEventManager().sendPlayerTryToJoinBannedEvent(model, player.getName());
            return true;
        }

        if (manager.isTempBanned(model.getUuid())) {

            if (model.isExpired()) {
                BanUtils.unban(model.getUuid().toString());
                return false;
            }
            disconnect(model, player);

            BanCache.addPunishment(player.getName(), model);

            EventManager.getEventManager().sendPlayerTryToJoinBannedEvent(model, player.getName());
            return true;
        }

        return false;

    }

    private void disconnect(BanModel model, PendingConnection player) {
        player.disconnect(getBaseComponent(model));
    }

    private BaseComponent getBaseComponent(BanModel model) {
        String message = null;
        if (model.isPermanent()) {
            if (model.isIpBan()) message = Messages.Ban.ipBannedMessagePermanent();
            else message = Messages.Ban.bannedMessagePermanent();

            return new TextComponent(message
                    .replace("{reason}", model.getReason())
                    .replace("{bannedBy}", model.getBannedBy())
                    .replace("{bannedOn}", TextUtils.formatDate(model.getTime(), Config.dateFormat()))
                    .replace("{banId}", String.valueOf(model.getId()))
            );
        } else {
            if (model.isIpBan()) message = Messages.Ban.ipBannedMessage();
            else message = Messages.Ban.tempBanMessageBase();

            return new TextComponent(message
                    .replace("{reason}", model.getReason())
                    .replace("{bannedBy}", model.getBannedBy())
                    .replace("{bannedOn}", TextUtils.formatDate(model.getTime(), Config.dateFormat()))
                    .replace("{expires}", TextUtils.formatDate(model.getUntil(), Config.dateFormat()))
                    .replace("{duration}", TextUtils.formatUptime(model.getUntil() - System.currentTimeMillis()))
                    .replace("{banId}", String.valueOf(model.getId()))
            );
        }
    }
}
