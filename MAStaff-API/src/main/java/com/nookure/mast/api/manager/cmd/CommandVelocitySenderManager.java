package com.nookure.mast.api.manager.cmd;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.nookure.mast.api.cmd.sender.CommandSender;
import com.nookure.mast.api.cmd.sender.ConsoleCommandSender;
import com.nookure.mast.api.cmd.sender.VelocityPlayerCommandSender;

public class CommandVelocitySenderManager extends CommandSenderManager<CommandSource> {
  @Inject
  private ConsoleCommandSender consoleCommandSender;

  @Override
  public CommandSender createCommandSender(CommandSource sender) {
    if (sender instanceof Player player) {
      return new VelocityPlayerCommandSender(player);
    }

    return consoleCommandSender;
  }
}
