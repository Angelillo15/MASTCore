package es.angelillo15.mast.bungee.listener.redis.staff;

import es.angelillo15.mast.api.event.EventHandler;
import es.angelillo15.mast.api.event.Listener;
import es.angelillo15.mast.api.redis.events.staff.StaffSwitchServerEvent;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.StaffUtils;

public class OnStaffSwitch implements Listener {
    @EventHandler
    public void onStaffSwitch(StaffSwitchServerEvent event) {
        StaffUtils.sendBroadcastPermissionMessage(Messages.getStaffChangeServer()
                        .replace("{player}", event.getStaffName())
                        .replace("{server}", event.getServerTo())
                        .replace("{fromServer}", event.getServerFrom()),
                "mast.staff.notify.change");
    }

}
