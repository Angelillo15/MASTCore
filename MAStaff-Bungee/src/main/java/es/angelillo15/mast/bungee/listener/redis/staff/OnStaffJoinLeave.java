package es.angelillo15.mast.bungee.listener.redis.staff;

import es.angelillo15.mast.api.event.EventHandler;
import es.angelillo15.mast.api.event.Listener;
import es.angelillo15.mast.api.redis.events.staff.join.StaffJoinEvent;
import es.angelillo15.mast.api.redis.events.staff.join.StaffLeaveEvent;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.StaffUtils;

public class OnStaffJoinLeave implements Listener {
    @EventHandler
    public void onStaffJoin(StaffJoinEvent event) {
        StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffJoin()
                        .replace("{player}", event.getStaffName())
                        .replace("{server}", "Proxy"),
                "mast.staff.notify.join");
    }

    @EventHandler
    public void onStaffLeave(StaffLeaveEvent event) {
        StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffLeave()
                        .replace("{player}", event.getStaffName()),
                "mast.staff.notify.leave");

    }
}