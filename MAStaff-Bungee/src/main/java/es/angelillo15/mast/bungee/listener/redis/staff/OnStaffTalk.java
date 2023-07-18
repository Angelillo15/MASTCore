package es.angelillo15.mast.bungee.listener.redis.staff;

import es.angelillo15.mast.api.redis.EventHandler;
import es.angelillo15.mast.api.redis.Listener;
import es.angelillo15.mast.api.redis.events.staff.StaffChatMessageEvent;
import es.angelillo15.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.StaffUtils;

public class OnStaffTalk implements Listener {
    @EventHandler
    public void onStaffTalk(StaffChatMessageEvent event) {
        String message = Messages.GET_STAFF_CHAT_FORMAT()
                .replace("{server}", event.getServer())
                .replace("{player}", event.getStaffName())
                .replace("{message}", event.getStaffMessage());

        StaffUtils.sendStaffChatMessage(message);
    }
}
