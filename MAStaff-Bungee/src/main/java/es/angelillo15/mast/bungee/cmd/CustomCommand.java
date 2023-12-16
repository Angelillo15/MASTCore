package es.angelillo15.mast.bungee.cmd;

import com.nookure.mast.api.cmd.sender.CommandSender;
import com.nookure.mast.api.manager.cmd.CommandBungeeSenderManager;
import com.nookure.mas.bungee.MAStaff;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CustomCommand extends Command implements TabExecutor {
  private final com.nookure.mast.api.cmd.Command command;
  private final CommandBungeeSenderManager senderManager = MAStaff
      .getInstance()
      .getInjector()
      .getInstance(CommandBungeeSenderManager.class);

  public CustomCommand(String name, com.nookure.mast.api.cmd.Command command) {
    super(name);
    this.command = command;
  }

  public CustomCommand(
      String name, String permission, com.nookure.mast.api.cmd.Command command) {
    super(name, permission);
    this.command = command;
  }

  public CustomCommand(
      String name,
      String permission,
      com.nookure.mast.api.cmd.Command command,
      String... aliases) {
    super(name, permission, aliases);
    this.command = command;
  }

  public CustomCommand(
      String name, com.nookure.mast.api.cmd.Command command, String... aliases) {
    super(name, null, aliases);
    this.command = command;
  }

  @Override
  public void execute(net.md_5.bungee.api.CommandSender sender, String[] args) {
    CommandSender pluginSender = senderManager.getSender(sender);

    if (pluginSender == null) {
      throw new NullPointerException("Sender is null");
    }

    command.onCommand(pluginSender, getName(), args);
  }

  @Override
  public Iterable<String> onTabComplete(net.md_5.bungee.api.CommandSender sender, String[] args) {
    CommandSender commandSender = senderManager.getSender(sender);

    if (commandSender == null) {
      throw new NullPointerException("Sender is null");
    }

    return this.command.onTabComplete(commandSender, args);
  }
}
