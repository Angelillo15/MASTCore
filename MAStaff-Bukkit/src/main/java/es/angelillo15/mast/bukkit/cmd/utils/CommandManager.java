package es.angelillo15.mast.bukkit.cmd.utils;


import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CommandManager {
    public static void sendCommandToConsole(Player player, String command) {
        if (command.startsWith("bungee:")) {
            sendCommandToBungeeConsole(player, command.replace("bungee:", ""));
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    public static void sendCommandToBungeeConsole(Player player, String command) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("mast-command");
            out.writeUTF(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.sendPluginMessage(MAStaff.getPlugin(), "BungeeCord", b.toByteArray());
    }

}
