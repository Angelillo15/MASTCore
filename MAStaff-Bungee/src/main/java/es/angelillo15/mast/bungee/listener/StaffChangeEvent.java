package es.angelillo15.mast.bungee.listener;

import com.nookure.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Messages;
import es.angelillo15.mast.bungee.utils.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffChangeEvent implements Listener {

  @EventHandler
  public void staffEnableEvent(PluginMessageEvent event) {
    if (!Objects.equals(event.getTag(), "mastaff:staff")) {
      return;
    }

    DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

    try {
      if (in.readUTF().equals("mast")) {
        String player = in.readUTF();
        String state = in.readUTF();

        if (state.equals("true")) {
          MAStaff.getInstance()
              .getLogger()
              .info(
                  TextUtils.colorize(
                      Messages.getPlayerStaffModeEnabled().replace("{player}", player)));
        } else {
          MAStaff.getInstance()
              .getLogger()
              .info(
                  TextUtils.colorize(
                      Messages.getPlayerStaffModeDisabled().replace("{player}", player)));
        }
      }
    } catch (IOException ignored) {

    }
  }
}
