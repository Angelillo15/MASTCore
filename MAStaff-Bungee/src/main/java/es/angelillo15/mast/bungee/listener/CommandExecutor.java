package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.api.redis.EventHandler;
import es.angelillo15.mast.bungee.MAStaff;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;

public class CommandExecutor implements Listener {
    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equals("mast-command")) {
            return;
        }

        String command = new String(event.getData());

        if (command.startsWith("bungee:")) {
            command = command.replace("bungee:", "");
        }

        MAStaff.getInstance().getProxy().getPluginManager().dispatchCommand(
                MAStaff.getInstance().getProxy().getConsole(), command
        );
    }
}
