package es.angelillo15.mast.api.managers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.cmd.sender.ConsoleCommandSender;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.cmd.sender.ProxiedPlayerCommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Singleton
public class CommandBungeeSenderManager extends CommandSenderManager<net.md_5.bungee.api.CommandSender> {
  @Inject
  private ConsoleCommandSender consoleCommandSender;
  @Override
  public CommandSender createCommandSender(net.md_5.bungee.api.CommandSender sender) {
    if (sender instanceof ProxiedPlayer) {
      return new ProxiedPlayerCommandSender((ProxiedPlayer) sender);
    }

    return consoleCommandSender;
  }
}
