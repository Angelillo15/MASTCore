package com.nookure.mast.api.manager.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.mast.api.cmd.sender.CommandSender;
import com.nookure.mast.api.cmd.sender.ConsoleCommandSender;
import com.nookure.mast.api.cmd.sender.PlayerCommandSender;
import org.bukkit.entity.Player;

@Singleton
public class CommandBukkitSenderManager extends CommandSenderManager<org.bukkit.command.CommandSender> {
  @Inject
  private ConsoleCommandSender consoleCommandSender;

  @Override
  public CommandSender createCommandSender(org.bukkit.command.CommandSender sender) {
    if (sender instanceof Player player) {
      return new PlayerCommandSender(player);
    }

    return consoleCommandSender;
  }
}
