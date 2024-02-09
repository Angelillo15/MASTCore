package com.nookure.staff.paper.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PaperCommandManager extends CommandManager {
  @Inject
  private Injector injector;
  private static final CommandMap commandMap;

  static {
    try {
      commandMap = (CommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
    } catch (Exception e) {
      throw new RuntimeException("Failed to get command map");
    }
  }

  @Override
  public void registerCommand(@NotNull Command command) {
    TemplateCommand templateCommand = new TemplateCommand(command);
    injector.injectMembers(templateCommand);

    commandMap.register(command.getCommandData().name(), templateCommand);
    command.prepare();
  }

  @Override
  public void unregisterCommand(@NotNull Command command) {
    org.bukkit.command.Command cmd = commandMap.getCommand(command.getCommandData().name());

    if (cmd != null) {
      cmd.unregister(commandMap);
    }
  }
}
