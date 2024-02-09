package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.util.AbstractLoader;
import com.nookure.staff.paper.command.PaperCommandManager;
import com.nookure.staff.paper.command.StaffModeCommand;
import com.nookure.staff.paper.command.main.NookureStaffCommand;
import com.nookure.staff.paper.handler.OnPlayerJoin;
import com.nookure.staff.paper.handler.OnPlayerLeave;
import com.nookure.staff.paper.handler.staff.OnStaffLeave;
import com.nookure.staff.paper.loader.ItemsLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.stream.Stream;

@Singleton
public class NookureStaff {
  @Inject
  private AbstractPluginConnection connection;
  @Inject
  private ConfigurationContainer<BukkitConfig> config;
  @Inject
  private JavaPlugin plugin;
  @Inject
  private Injector injector;
  @Inject
  private Logger logger;
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  @Inject
  private PaperCommandManager commandManager;
  private final ArrayList<Listener> listeners = new ArrayList<>();

  public void onEnable() {
    loadDatabase();
    loadListeners();
    loadLoaders();
    loadCommands();

    registeringOnlinePlayers();
  }

  private void loadDatabase() {
    connection.connect(config.get().database);
  }

  private void loadListeners() {
    registerListener(OnPlayerJoin.class);
    registerListener(OnPlayerLeave.class);
    registerListener(OnStaffLeave.class);
  }

  public void registerListener(Class<? extends Listener> listener) {
    logger.debug("Registering listener %s", listener.getSimpleName());

    Listener instance = injector.getInstance(listener);
    listeners.add(instance);
    Bukkit.getPluginManager().registerEvents(instance, plugin);
  }

  /**
   * Why this ?
   * Because if you do a reload of the server, and you have online players,
   * the players will not be registered in the player wrapper manager.
   */
  public void registeringOnlinePlayers() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      logger.debug("Creating player wrapper for %s", player.getName());

      if (player.hasPermission(Permissions.STAFF_PERMISSION)) {
        StaffPaperPlayerWrapper playerWrapper = StaffPaperPlayerWrapper.Builder
            .create(injector)
            .setPlayer(player)
            .build();

        playerWrapperManager.addPlayerWrapper(player, playerWrapper, true);

        logger.debug("Player %s has staff permission, adding staff player wrapper", player.getName());
        return;
      }

      PaperPlayerWrapper playerWrapper = PaperPlayerWrapper.Builder
          .create(injector)
          .setPlayer(player)
          .build();

      playerWrapperManager.addPlayerWrapper(player, playerWrapper);

      logger.debug("Player %s does not have staff permission, adding player wrapper", player.getName());
    });
  }

  private void loadLoaders() {
    Stream.of(
        injector.getInstance(ItemsLoader.class)
    ).forEach(AbstractLoader::load);
  }

  private void loadCommands() {
    commandManager.registerCommand(injector.getInstance(NookureStaffCommand.class));

    if (config.get().modules.isStaffMode()) {
      commandManager.registerCommand(injector.getInstance(StaffModeCommand.class));
    }
  }

  public void onDisable() {

  }

  public void reload() {

  }
}
