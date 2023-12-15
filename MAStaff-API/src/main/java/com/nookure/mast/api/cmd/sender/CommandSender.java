package com.nookure.mast.api.cmd.sender;

import java.io.Serializable;

public interface CommandSender extends Serializable {
  void sendMessage(String message);

  boolean hasPermission(String permission);

  String getName();

  String getUniqueId();

  String getAddress();

  boolean isPlayer();

  boolean isConsole();

  boolean isProxy();

  boolean isBungee();

  boolean isSpigot();

  String getServerName();
}
