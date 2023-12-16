package com.nookure.mast.api.cmd.sender;

import es.angelillo15.mast.api.TextUtils;
import org.bukkit.entity.Player;

public class PlayerCommandSender implements CommandSender {
  private final Player player;

  public PlayerCommandSender(Player player) {
    this.player = player;
  }

  @Override
  public void sendMessage(String message) {
    TextUtils.sendMessage(player, message);
  }

  @Override
  public boolean hasPermission(String permission) {
    return player.hasPermission(permission);
  }

  @Override
  public String getName() {
    return player.getName();
  }

  @Override
  public boolean isPlayer() {
    return true;
  }

  @Override
  public boolean isConsole() {
    return false;
  }

  @Override
  public boolean isProxy() {
    return false;
  }

  @Override
  public boolean isBungee() {
    return false;
  }

  @Override
  public boolean isSpigot() {
    return true;
  }

  @Override
  public String getUniqueId() {
    return player.getUniqueId().toString();
  }

  @Override
  public String getAddress() {
    return player.getAddress().getAddress().getHostAddress().split(":")[0];
  }

  @Override
  public String getServerName() {
    return player.getServer().getName();
  }

  public Player getPlayer() {
    return player;
  }
}
