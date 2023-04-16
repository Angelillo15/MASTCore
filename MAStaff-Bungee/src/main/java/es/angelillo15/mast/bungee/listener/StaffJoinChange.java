package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.api.event.bungee.staff.StaffJoinEvent;
import es.angelillo15.mast.api.event.bungee.staff.StaffLeaveEvent;
import es.angelillo15.mast.api.event.bungee.staff.StaffSwitchServerEvent;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.manager.RedisManager;
import es.angelillo15.mast.bungee.utils.PreviousServerManager;
import es.angelillo15.mast.bungee.utils.StaffUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffJoinChange implements Listener {
    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission("mast.staff.join")) {
            return;
        }

        StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffJoin()
                        .replace("{player}", player.getName())
                        .replace("{server}", "Proxy")
                , "mast.staff.notify.join");

        ProxyServer.getInstance().getPluginManager().callEvent(new StaffJoinEvent(player));

        if (!Config.Redis.isEnabled()) {
            return;
        }

        es.angelillo15.mast.api.redis.events.staff.join.StaffJoinEvent staffJoinEvent =
                new es.angelillo15.mast.api.redis.events.staff.join.StaffJoinEvent(Config.Redis.getServerName(), player
                );

        RedisManager.sendEvent(staffJoinEvent);
    }

    @EventHandler
    public void onSuccessfullyChange(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.getServer() == null) {
            return;
        }

        if (!player.hasPermission("mast.staff.change")) {
            return;
        }

        String fromServer;

        fromServer = "Proxy";

        if (PreviousServerManager.hasPreviousServer(player.getUniqueId())) {
            fromServer = PreviousServerManager.getPreviousServer(player.getUniqueId());
        } else {
            PreviousServerManager.setPreviousServer(player.getUniqueId(), "Proxy");
        }

        StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffChangeServer()
                        .replace("{player}", player.getDisplayName())
                        .replace("{server}", player.getServer().getInfo().getName())
                        .replace("{fromServer}", PreviousServerManager.getPreviousServer(player.getUniqueId())),
                "mast.staff.notify.change");

        ProxyServer.getInstance().getPluginManager().callEvent(new StaffSwitchServerEvent(event.getPlayer(),
                fromServer,
                player.getServer().getInfo().getName())
        );

        if (Config.Redis.isEnabled()) {
            es.angelillo15.mast.api.redis.events.staff.StaffSwitchServerEvent staffSwitchServerEvent =
                    new es.angelillo15.mast.api.redis.events.staff.StaffSwitchServerEvent(Config.Redis.getServerName(),
                            player.getDisplayName(),
                            fromServer,
                            player.getServer().getInfo().getName()
                    );

            RedisManager.sendEvent(staffSwitchServerEvent);
        }

        PreviousServerManager.setPreviousServer(player.getUniqueId(), player.getServer().
                getInfo().
                getName()
        );
    }


    @EventHandler
    public void leaveServer(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("mast.staff.leave")) {
            StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffLeave()
                            .replace("{player}", player.getDisplayName()),
                    "mast.staff.notify.leave");

        }

        ProxyServer.getInstance().getPluginManager().callEvent(new StaffLeaveEvent(player));

        PreviousServerManager.removePreviousServer(player.getUniqueId());

        if (!Config.Redis.isEnabled()) {
            return;
        }

        es.angelillo15.mast.api.redis.events.staff.join.StaffLeaveEvent staffLeaveEvent =
                new es.angelillo15.mast.api.redis.events.staff.join.StaffLeaveEvent(Config.Redis.getServerName(), player
                );

        RedisManager.sendEvent(staffLeaveEvent);
    }
}
