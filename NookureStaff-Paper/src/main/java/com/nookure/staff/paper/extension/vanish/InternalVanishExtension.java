package com.nookure.staff.paper.extension.vanish;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.extension.VanishExtension;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.paper.StaffPaperPlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InternalVanishExtension extends VanishExtension {
  private final StaffPaperPlayerWrapper player;
  @Inject
  private Logger logger;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  @Inject
  private JavaPlugin javaPlugin;

  private boolean vanished = false;

  public InternalVanishExtension(StaffPlayerWrapper player) {
    super(player);
    this.player = (StaffPaperPlayerWrapper) player;
  }

  @Override
  public void enableVanish(boolean silent) {
    logger.debug("Enabling vanish for %s", player.getName());

    if (!silent) {
      player.sendMiniMessage(messages.get().vanish.vanishEnabled());

      playerWrapperManager.stream()
          .forEach(p ->
              p.sendMiniMessage(messages.get().vanish.vanishEnabledBroadcast(), "player", player.getName())
          );
    }

    Bukkit.getOnlinePlayers().stream()
        .filter(p -> !p.hasPermission(Permissions.STAFF_VANISH_SEE))
        .forEach(p -> p.hidePlayer(javaPlugin, player.getPlayer()));

    setVanished(true);
  }

  @Override
  public void disableVanish(boolean silent) {
    logger.debug("Disabling vanish for %s", player.getName());

    if (!silent) {
      player.sendMiniMessage(messages.get().vanish.vanishDisabled());

      playerWrapperManager.stream()
          .forEach(p ->
              p.sendMiniMessage(messages.get().vanish.vanishDisabledBroadcast(), "player", player.getName())
          );
    }

    Bukkit.getOnlinePlayers()
        .forEach(p -> p.showPlayer(javaPlugin, player.getPlayer()));

    setVanished(false);
  }

  @Override
  public boolean isVanished() {
    return vanished;
  }

  @Override
  public void setVanished(boolean vanished) {
    this.vanished = vanished;
  }
}
