package es.angelillo15.mast.bungee.punishments.listeners;

import com.craftmend.storm.Storm;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.config.punishments.Config;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.models.BansTable;
import es.angelillo15.mast.api.models.IpBansTable;
import es.angelillo15.mast.api.punishments.events.EventManager;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.sql.SQLException;

public class PlayerBanListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        event.registerIntent((Plugin) MAStaffInstance.getBungeeInstance());
        PendingConnection connection = event.getConnection();

        if (banCheck(connection)) {
            event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
            return;
        }

        ipBanCheck(connection);

        event.completeIntent((Plugin) MAStaffInstance.getBungeeInstance());
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        Storm storm = PluginConnection.getStorm();
        new Thread(() -> {
            BansTable table = BansTable.getBan(event.getPlayer().getUniqueId());

            if (table != null && table.getUsername() != event.getPlayer().getName()) {
                table.setUsername(event.getPlayer().getName());
                table.setActive(true);

                try {
                    storm.save(table);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            BansTable tableWithUsername = BansTable.getBan(event.getPlayer().getName());

            if (tableWithUsername != null && tableWithUsername.getUuid() != event.getPlayer().getUniqueId().toString()) {
                tableWithUsername.setUuid(event.getPlayer().getUniqueId().toString());
                tableWithUsername.setActive(true);

                try {
                    storm.save(tableWithUsername);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }

            banCheck(event.getPlayer().getPendingConnection());
        }).start();
    }

    public boolean banCheck(PendingConnection connection) {
        if (!BansTable.isBanned(connection.getName())) return false;

        try {
            BansTable ban = BansTable.getBan(connection.getName());
            connection.disconnect(getBaseComponent(ban));
        } catch (Exception ignored) {
        }

        return true;
    }

    public boolean ipBanCheck(PendingConnection connection) {
        BansTable table = IpBansTable.getIpBanned(connection.getAddress().getAddress().getHostAddress());

        if (table == null) return false;

        connection.disconnect(getBaseComponent(table));

        return true;
    }

    private BaseComponent getBaseComponent(BansTable model) {
        String message = null;
        EventManager.getEventManager().sendPlayerTryToJoinBannedEvent(model, model.getUsername());

        if (model.isPermanent()) {
            if (model.getIpban()) message = Messages.Ban.ipBannedMessagePermanent();
            else message = Messages.Ban.bannedMessagePermanent();

            return new TextComponent(message
                    .replace("{reason}", model.getReason())
                    .replace("{bannedBy}", model.getBanned_by_name())
                    .replace("{bannedOn}", TextUtils.formatDate(model.getTime(), Config.dateFormat()))
                    .replace("{banId}", String.valueOf(model.getId()))
            );
        } else {
            if (model.getIpban()) message = Messages.Ban.ipBannedMessage();
            else message = Messages.Ban.tempBanMessageBase();

            return new TextComponent(message
                    .replace("{reason}", model.getReason())
                    .replace("{bannedBy}", model.getBanned_by_name())
                    .replace("{bannedOn}", TextUtils.formatDate(model.getTime(), Config.dateFormat()))
                    .replace("{expires}", TextUtils.formatDate(model.getUntil(), Config.dateFormat()))
                    .replace("{duration}", TextUtils.formatUptime(model.getUntil() - System.currentTimeMillis()))
                    .replace("{banId}", String.valueOf(model.getId()))
            );
        }
    }
}
