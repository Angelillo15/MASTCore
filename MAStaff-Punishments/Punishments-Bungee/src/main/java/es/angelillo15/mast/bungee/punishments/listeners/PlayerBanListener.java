package es.angelillo15.mast.bungee.punishments.listeners;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.event.bungee.ban.PlayerBannedEvent;
import es.angelillo15.mast.api.models.BanModel;
import es.angelillo15.mast.api.punishments.cache.BanCache;
import es.angelillo15.mast.api.punishments.config.Config;
import es.angelillo15.mast.api.punishments.config.Messages;
import es.angelillo15.mast.api.punishments.data.AbstractDataManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PlayerBanListener implements Listener {
    @EventHandler
    public void onJoin(PreLoginEvent event) {
        PendingConnection conn = event.getConnection();
        event.registerIntent((Plugin) MAStaffInstance.getBungeeInstance());

        if (cacheCheck(conn)) return;
        databaseCheck(conn);

        event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
    }

    @EventHandler
    public void onPlayerBan(PlayerBannedEvent event) {
        ProxiedPlayer banned = ProxyServer.getInstance().getPlayer(event.getBanModel().getUuid());

        if (banned != null) {
            banned.disconnect(getBaseComponent(event.getBanModel()));
        }
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
            return true;
        }


        return false;
    }

    private boolean databaseCheck(PendingConnection player) {
        MAStaffInstance.getLogger().debug("Checking database for " + player.getName() + "'s ban");

        UserData userData = null;

        userData = DataManager.getDataManager().getUserData(player.getName());

        AbstractDataManager manager =
                es.angelillo15.mast.api.punishments.data.DataManager.getDataManager();

        if (manager.isPermBanned(userData.getUUID())) {

            BanModel model = manager.getBan(UUID.fromString(userData.getUUID()));
            disconnect(model, player);

            BanCache.addPunishment(player.getName(), model);
            return true;
        }

        return false;

    }

    private void disconnect(BanModel model, PendingConnection player) {
        player.disconnect(getBaseComponent(model));
    }

    private BaseComponent getBaseComponent(BanModel model) {
        if (model.isPermanent()) {
            return new TextComponent(Messages.Ban.bannedMessagePermanent()
                    .replace("{reason}", model.getReason())
                    .replace("{bannedBy}", model.getBannedBy())
                    .replace("{bannedOn}", TextUtils.formatDate(model.getTime(), Config.dateFormat()))
            );
        } else {
            return new TextComponent(
                    Messages.Ban.tempBanMessageBase()
                            .replace("{reason}", model.getReason())
                            .replace("{bannedBy}", model.getBannedBy())
                            .replace("{bannedOn}", TextUtils.formatDate(model.getTime(), Config.dateFormat()))
                            .replace("{expires}", TextUtils.formatDate(model.getUntil(), Config.dateFormat()))
                            .replace("{duration}", TextUtils.formatUptime(model.getUntil()))
            );
        }
    }
}
