package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.StaffUtils;
import es.angelillo15.mast.utils.StaffChatManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffTalkEvent implements Listener {

    @EventHandler
    public void onStaffChat(ChatEvent e) {
        ProxiedPlayer player = (ProxiedPlayer) e.getSender();
        if(e.getMessage().startsWith("/")) return;

        if(!(player.hasPermission("mast.staffchat"))){
            MASTBungeeManager.getInstance().getLogger().info("Player " + player.getName() + " tried to use staff chat without permission.");
            return;
        }

        if(e.getMessage().startsWith("#") || StaffChatManager.isStaffChatEnable(player.getUniqueId().toString())){
            e.setCancelled(true);
            String text;
            if(e.getMessage().startsWith("#")){
                text = e.getMessage().substring(1);
            } else {
                text = e.getMessage();
            }
            String message = Messages.GET_STAFF_CHAT_FORMAT()
                    .replace("{server}",player.getServer().getInfo().getName())
                    .replace("{player}", player.getName())
                    .replace("{message}", text);
            StaffUtils.sendStaffChatMessage(message);
        }

    }
}
