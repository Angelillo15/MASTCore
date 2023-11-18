package es.angelillo15.mast.bukkit.cmd.utils;


import es.angelillo15.mast.bukkit.MAStaff;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandManager {
  private static CommandMap commandMap;
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

    player.sendPluginMessage(MAStaff.getPlugin(), "mastaff:commands", b.toByteArray());
  }

  public static void registerIntoCommandMap(@NotNull Command command) {
    if (getCommandMap() == null) {
      throw new RuntimeException("CommandMap is null");
    }

    getCommandMap().register("mastaff", command);
  }

  @Nullable
  public static CommandMap getCommandMap() {
    if (commandMap != null) {
      return commandMap;
    }

    try {
      Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
      commandMapField.setAccessible(true);
      commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }

    return commandMap;
  }
}
