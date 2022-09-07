package es.angelillo15.mast.bungee.listener;

import es.angelillo15.mast.bungee.MASTBungee;
import es.angelillo15.mast.bungee.MASTBungeeManager;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;

public class StaffChangeEvent implements Listener {

    @EventHandler
    public void staffEnableEvent(PluginMessageEvent event) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

        try {
            if (in.readUTF().equals("mast")) {
                String player = in.readUTF();
                String state = in.readUTF();

                MASTBungeeManager

            }
        } catch (IOException ignored) {

        }

    }



}
