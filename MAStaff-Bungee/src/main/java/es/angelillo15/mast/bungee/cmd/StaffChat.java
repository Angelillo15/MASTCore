package es.angelillo15.mast.bungee.cmd;

import es.angelillo15.mast.api.event.bungee.staffchat.StaffChatTalkEvent;
import es.angelillo15.mast.api.redis.events.staff.StaffChatMessageEvent;
import es.angelillo15.mast.bungee.config.Config;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.manager.RedisManager;
import es.angelillo15.mast.bungee.utils.StaffUtils;
import es.angelillo15.mast.api.managers.StaffChatManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChat extends Command {
    public StaffChat() {
        super("sc", "mast.staffchat", "staffchat");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(p.hasPermission("mast.staffchat")){
                if(args.length < 1){
                    if(StaffChatManager.isStaffChatEnable(p.getUniqueId().toString())){
                        p.sendMessage(new TextComponent(Messages.getStaffChatDisabled()));
                        StaffChatManager.setStaffChatEnable(p.getUniqueId().toString(), false);
                    } else{
                        p.sendMessage(new TextComponent(Messages.getStaffChatEnabled()));
                        StaffChatManager.setStaffChatEnable(p.getUniqueId().toString(), true);
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String arg : args) {
                        sb.append(arg).append(" ");
                    }
                    String message = Messages.GET_STAFF_CHAT_FORMAT()
                            .replace("{server}",p.getServer().getInfo().getName())
                            .replace("{player}", p.getName())
                            .replace("{message}", sb.toString()
                            );
                    StaffUtils.sendStaffChatMessage(message);

                    if (Config.Redis.isEnabled()) {
                        StaffChatMessageEvent staffChatMessageEvent = new StaffChatMessageEvent(Config.Redis.getServerName(),
                                p.getDisplayName(),
                                message,
                                p.getServer().getInfo().getName()
                        );

                        RedisManager.sendEvent(staffChatMessageEvent);
                    }

                    ProxyServer.getInstance().getPluginManager().callEvent(new StaffChatTalkEvent(p, sb.toString()));
                }
            }
        }
    }
}
