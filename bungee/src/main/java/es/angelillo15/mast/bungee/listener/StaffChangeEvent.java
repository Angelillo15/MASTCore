package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.TextUtils;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class StaffChangeEvent implements Listener {

    @EventHandler
    public void staffEnableEvent(PluginMessageEvent event) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

        try {
            if (in.readUTF().equals("mast")) {
                String player = in.readUTF();
                String state = in.readUTF();

                if (state.equals("true")) {
                    MASTBungeeManager.getInstance().getLogger().info(TextUtils.colorize(
                            Messages.getPlayerStaffModeEnabled().replace("{player}", player)
                    ));
                    MASTBungeeManager.setStaffCount(MASTBungeeManager.getStaffCount() + 1);
                } else {
                    MASTBungeeManager.getInstance().getLogger().info(TextUtils.colorize(
                            Messages.getPlayerStaffModeDisabled().replace("{player}", player)
                    ));
                    MASTBungeeManager.setStaffCount(MASTBungeeManager.getStaffCount() - 1);
                }
            }
        } catch (IOException ignored) {

        }

    }


}
