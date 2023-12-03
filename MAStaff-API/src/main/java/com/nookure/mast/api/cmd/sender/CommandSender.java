package com.nookure.mast.api.cmd.sender;

import net.kyori.adventure.audience.Audience;

public interface CommandSender {
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

  Audience getAudience();

  String getServerName();
}
