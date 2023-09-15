package es.angelillo15.mast.api.managers;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.cmd.sender.ConsoleCommandSender;
import es.angelillo15.mast.api.cmd.sender.VelocityPlayerCommandSender;

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
