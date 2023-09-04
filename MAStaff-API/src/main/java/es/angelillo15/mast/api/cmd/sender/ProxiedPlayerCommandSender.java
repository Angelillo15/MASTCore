package es.angelillo15.mast.api.cmd.sender;

import es.angelillo15.mast.api.TextUtils;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxiedPlayerCommandSender implements CommandSender {
  private final ProxiedPlayer player;
  private final Audience audience;

  public ProxiedPlayerCommandSender(ProxiedPlayer player) {
    this.player = player;
    this.audience = TextUtils.getAudience(player);
  }

  @Override
  public void sendMessage(String message) {
    player.sendMessage(new TextComponent(TextUtils.colorize(message)));
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
  public String getUniqueId() {
    return player.getUniqueId().toString();
  }

  @Override
  public String getAddress() {
    return player.getAddress().getAddress().getHostAddress().split(":")[0];
  }

  @Override
  public Audience getAudience() {
    return audience;
  }

  @Override
  public String getServerName() {
    return player.getServer().getInfo().getName();
  }
}
