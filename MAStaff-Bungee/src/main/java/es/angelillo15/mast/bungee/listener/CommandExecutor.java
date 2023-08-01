package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.TextUtils;
import lombok.SneakyThrows;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;

public class CommandExecutor implements Listener {
    @SneakyThrows
    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!Objects.equals(event.getTag(), "mastaff:commands")) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

        if (!in.readUTF().equals("mast-command")) {
            return;
        }

        String command = in.readUTF();

        if (command.startsWith("bungee:")) {
            command = command.replace("bungee:", "");
        }

        MAStaffInstance.getLogger().debug("Executing command: " + command);

        MAStaff.getInstance().getProxy().getPluginManager().dispatchCommand(
                MAStaff.getInstance().getProxy().getConsole(), command
        );
    }
}
