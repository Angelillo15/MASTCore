package com.nookure.mast.api.cmd.sender;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.ILogger;

@Singleton
public class ConsoleCommandSender implements CommandSender {
  @Inject
  private ILogger logger;
  @Override
  public void sendMessage(String message) {
    logger.info(message);
  }

  @Override
  public boolean hasPermission(String permission) {
    return true;
  }

  @Override
  public String getName() {
    return "Console";
  }

  @Override
  public String getUniqueId() {
    return "CONSOLE";
  }

  @Override
  public boolean isPlayer() {
    return false;
  }

  @Override
  public boolean isConsole() {
    return true;
  }

  @Override
  public boolean isProxy() {
    return true;
  }

  @Override
  public boolean isBungee() {
    return true;
  }

  @Override
  public boolean isSpigot() {
    return false;
  }

  @Override
  public String getAddress() {
    return "0.0.0.0";
  }

  @Override
  public String getServerName() {
    return "Proxy";
  }
}
