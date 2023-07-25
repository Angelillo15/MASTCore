package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.api.event.bungee.staffchat.StaffChatTalkEvent;
import es.angelillo15.mast.api.redis.events.staff.StaffChatMessageEvent;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.manager.RedisManager;
import es.angelillo15.mast.bungee.utils.StaffUtils;
import es.angelillo15.mast.api.managers.LegacyStaffChatManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffTalkEvent implements Listener {

    @EventHandler
    public void onStaffChat(ChatEvent e) {
        if(!(e.getSender() instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) e.getSender();
        if(e.getMessage().startsWith("/")) return;

        if(!(player.hasPermission("mast.staffchat"))){
            return;
        }

        if(e.getMessage().startsWith("#") || LegacyStaffChatManager.isStaffChatEnable(player.getUniqueId().toString())){
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

            ProxyServer.getInstance().getPluginManager().callEvent(new StaffChatTalkEvent(player, text));

            if (Config.Redis.isEnabled()) {
                StaffChatMessageEvent staffChatMessageEvent = new StaffChatMessageEvent(Config.Redis.getServerName(),
                        player.getDisplayName(),
                        text,
                        player.getServer().getInfo().getName()
                );

                RedisManager.sendEvent(staffChatMessageEvent);
            }
        }

    }
}
