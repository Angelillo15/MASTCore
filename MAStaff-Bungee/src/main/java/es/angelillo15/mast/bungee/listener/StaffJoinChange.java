package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.StaffUtils;
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
        if(player.hasPermission("mast.staff.join")){
            StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffJoin()
                    .replace("{player}", player.getName())
                    .replace("{server}", "Proxy")
            , "mast.staff.notify.join");
        }
    }

    @EventHandler
    public void onSuccessfullyChange(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if(player.getServer() == null){
            return;
        }
        if (player.hasPermission("mast.staff.change")) {
            if(!MASTBungeeManager.getInstance().getPreviousServer().containsKey(player.getUniqueId())){
                MASTBungeeManager.getInstance().setPreviousServer(player.getUniqueId(), "Proxy");
            }
            StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffChangeServer()
                    .replace("{player}", player.getDisplayName())
                    .replace("{server}", player.getServer().getInfo().getName())
                    .replace("{fromServer}", MASTBungeeManager.getInstance().getPreviousServer(player.getUniqueId())),
                    "mast.staff.notify.change");
        }

        MASTBungeeManager.getInstance().setPreviousServer(player.getUniqueId(), player.getServer().getInfo().getName());
    }

    @EventHandler
    public void leaveServer(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("mast.staff.leave")) {
            StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffLeave()
                    .replace("{player}", player.getDisplayName()),
                    "mast.staff.notify.leave");
        }
        MASTBungeeManager.getInstance().removePreviousServer(player.getUniqueId());
    }
}
