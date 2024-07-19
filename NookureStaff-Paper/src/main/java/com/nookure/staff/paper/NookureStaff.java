package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.addons.AddonManager;
import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.config.bukkit.StaffModeBlockedCommands;
import com.nookure.staff.api.config.bukkit.partials.VanishType;
import com.nookure.staff.api.config.bukkit.partials.messages.note.NoteMessages;
import com.nookure.staff.api.config.messaging.MessengerConfig;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.extension.StaffPlayerExtensionManager;
import com.nookure.staff.api.extension.VanishExtension;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.messaging.Channels;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.util.AbstractLoader;
import com.nookure.staff.paper.bootstrap.StaffBootstrapper;
import com.nookure.staff.paper.command.*;
import com.nookure.staff.paper.command.main.NookureStaffCommand;
import com.nookure.staff.paper.command.staff.StaffCommandParent;
import com.nookure.staff.paper.extension.FreezePlayerExtension;
import com.nookure.staff.paper.extension.vanish.InternalVanishExtension;
import com.nookure.staff.paper.extension.vanish.SuperVanishExtension;
import com.nookure.staff.paper.listener.OnPlayerJoin;
import com.nookure.staff.paper.listener.OnPlayerLeave;
import com.nookure.staff.paper.listener.freeze.OnFreezePlayerInteract;
import com.nookure.staff.paper.listener.freeze.OnFreezePlayerMove;
import com.nookure.staff.paper.listener.freeze.OnFreezePlayerQuit;
import com.nookure.staff.paper.listener.freeze.OnPlayerChatFreeze;
import com.nookure.staff.paper.listener.player.OnPlayerDataJoin;
import com.nookure.staff.paper.listener.server.OnServerBroadcast;
import com.nookure.staff.paper.listener.staff.OnPlayerInStaffChatTalk;
import com.nookure.staff.paper.listener.staff.OnShiftAndRightClick;
import com.nookure.staff.paper.listener.staff.OnStaffLeave;
import com.nookure.staff.paper.listener.staff.command.OnStaffPlayerCommand;
import com.nookure.staff.paper.listener.staff.items.OnInventoryClick;
import com.nookure.staff.paper.listener.staff.items.OnPlayerEntityInteract;
import com.nookure.staff.paper.listener.staff.items.OnPlayerInteract;
import com.nookure.staff.paper.listener.staff.items.OnPlayerInventoryClick;
import com.nookure.staff.paper.listener.staff.state.*;
import com.nookure.staff.paper.listener.staff.vanish.PlayerVanishListener;
import com.nookure.staff.paper.listener.staff.vanish.StaffVanishListener;
import com.nookure.staff.paper.loader.AddonsLoader;
import com.nookure.staff.paper.loader.InventoryLoader;
import com.nookure.staff.paper.loader.ItemsLoader;
import com.nookure.staff.paper.loader.PlaceholderApiLoader;
import com.nookure.staff.paper.messaging.BackendMessageMessenger;
import com.nookure.staff.paper.note.command.ParentNoteCommand;
import com.nookure.staff.paper.note.listener.OnPlayerNoteJoin;
import com.nookure.staff.paper.task.FreezeSpamMessage;
import com.nookure.staff.paper.task.FreezeTimerTask;
import com.nookure.staff.paper.task.StaffModeActionbar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class NookureStaff {
  private final ArrayList<Listener> listeners = new ArrayList<>();
  private final List<Class<? extends AbstractLoader>> loadersClass;
  private final List<AbstractLoader> loaders = new ArrayList<>();

  {
    loadersClass = List.of(
        AddonsLoader.class,
        InventoryLoader.class,
        ItemsLoader.class,
        PlaceholderApiLoader.class
    );
  }

  @Inject
  private AbstractPluginConnection connection;
  @Inject
  private ConfigurationContainer<BukkitConfig> config;
  @Inject
  private ConfigurationContainer<MessengerConfig> messengerConfig;
  @Inject
  private ConfigurationContainer<ItemsConfig> itemsConfig;
  @Inject
  private ConfigurationContainer<BukkitMessages> messagesConfig;
  @Inject
  private ConfigurationContainer<StaffModeBlockedCommands> staffModeBlockedCommands;
  @Inject
  private ConfigurationContainer<NoteMessages> noteMessages;
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
  @Inject
  private EventManager eventManager;
  @Inject
  private StaffPlayerExtensionManager extensionManager;
  @Inject
  private AddonManager addonManager;

  public void onEnable() {
    loadDatabase();
    loadListeners();
    loadBukkitListeners();
    loadLoaders();
    loadCommands();
    loadExtensions();
    loadTasks();

    registeringOnlinePlayers();
  }

  private void loadDatabase() {
    connection.connect(config.get().database, plugin.getClass().getClassLoader());
  }

  private void loadListeners() {
    switch (messengerConfig.get().getType()) {
      case REDIS -> {
        logger.debug("Registering Redis messenger...");
        injector.getInstance(EventMessenger.class).prepare();
        logger.debug("Redis messenger registered");
      }
      case NONE -> logger.debug("No messenger type was found, events will not be sent");
    }

    Bukkit.getMessenger().registerIncomingPluginChannel(plugin, Channels.EVENTS, injector.getInstance(BackendMessageMessenger.class));
    Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, Channels.EVENTS);

    logger.debug("Registering PM");
    eventManager.registerListener(injector.getInstance(OnServerBroadcast.class));
  }

  private void loadBukkitListeners() {
    Stream.of(
        OnPlayerJoin.class,
        OnPlayerLeave.class,
        OnPlayerInventoryClick.class
    ).forEach(this::registerListener);

    if (config.get().modules.isStaffMode()) {
      Stream.of(
          OnStaffLeave.class,
          OnInventoryClick.class,
          OnPlayerInteract.class,
          OnPlayerEntityInteract.class,
          OnBlockReceiveGameEvent.class,
          OnEntityTarget.class,
          OnFoodLevelChange.class,
          OnItemDrop.class,
          OnItemGet.class,
          OnItemSwap.class,
          OnPlayerAttack.class,
          OnWorldChange.class,
          OnStaffPlayerCommand.class
      ).forEach(this::registerListener);
    }

    if (config.get().staffMode.silentChestOpen()) {
      registerListener(OnOpenChest.class);
    }

    if (config.get().modules.isVanish() && config.get().staffMode.vanishType() == VanishType.INTERNAL_VANISH) {
      Stream.of(
          PlayerVanishListener.class,
          StaffVanishListener.class
      ).forEach(this::registerListener);
    }

    if (config.get().modules.isFreeze()) {
      Stream.of(
          OnFreezePlayerInteract.class,
          OnFreezePlayerMove.class,
          OnFreezePlayerQuit.class
      ).forEach(this::registerListener);

      if (StaffBootstrapper.isPaper) {
        registerListener(OnPlayerChatFreeze.class);
      }
    }

    if (config.get().modules.isStaffChat() && StaffBootstrapper.isPaper) {
      registerListener(OnPlayerInStaffChatTalk.class);
    }

    if (config.get().modules.isPlayerData()) {
      registerListener(OnPlayerDataJoin.class);
    }

    if (config.get().modules.isPlayerData() && config.get().modules.isUserNotes()) {
      registerListener(OnPlayerNoteJoin.class);
    }

    if (config.get().playerActions.shiftAndRightClickToInspect()) {
      if (config.get().modules.isPlayerList() && config.get().modules.isPlayerData() && config.get().modules.isUserNotes()) {
        registerListener(OnShiftAndRightClick.class);
      } else {
        logger.warning("PlayerActions module requires PlayerList, PlayerData and UserNotes modules to be enabled, disabling PlayerActions module");
      }
    }
  }

  public void registerListener(Class<? extends Listener> listener) {
    logger.debug("Registering listener %s", listener.getName());

    Listener instance = injector.getInstance(listener);
    listeners.add(instance);
    Bukkit.getPluginManager().registerEvents(instance, plugin);
  }

  public void unregisterListeners() {
    HandlerList.getHandlerLists().forEach(listener -> listeners.forEach(listener::unregister));

    listeners.clear();
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
    loadersClass.forEach(clazz -> {
      AbstractLoader loader = injector.getInstance(clazz);
      loaders.add(loader);
    });

    loaders.forEach(AbstractLoader::load);
  }

  private void loadTasks() {
    if (config.get().modules.isFreeze() && config.get().freeze.freezeTimer() != -1) {
      Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, injector.getInstance(FreezeTimerTask.class), 0, 20);
    }

    if (config.get().modules.isFreeze()) {
      Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, injector.getInstance(FreezeSpamMessage.class), 0, 20 * 5);
    }

    if (config.get().staffMode.actionBar()) {
      Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, injector.getInstance(StaffModeActionbar.class), 0, 20);
    }
  }

  private void loadCommands() {
    commandManager.registerCommand(injector.getInstance(NookureStaffCommand.class));

    if (config.get().modules.isStaffMode()) {
      commandManager.registerCommand(injector.getInstance(StaffCommandParent.class));
    }

    if (config.get().modules.isFreeze()) {
      commandManager.registerCommand(injector.getInstance(FreezeCommand.class));
      commandManager.registerCommand(injector.getInstance(FreezeChatCommand.class));
    }

    if (config.get().modules.isStaffChat()) {
      commandManager.registerCommand(injector.getInstance(StaffChatCommand.class));
    }

    if (config.get().modules.isVanish() && config.get().staffMode.vanishType() == VanishType.INTERNAL_VANISH) {
      commandManager.registerCommand(injector.getInstance(VanishCommand.class));
    }

    if (config.get().modules.isPlayerData() && config.get().modules.isUserNotes()) {
      commandManager.registerCommand(injector.getInstance(ParentNoteCommand.class));
    }

    if (config.get().modules.isPlayerList()) {
      if (!config.get().modules.isPlayerData()) {
        logger.severe("PlayerList module requires PlayerData module to be enabled, disabling PlayerList module");
      } else {
        if (!config.get().modules.isUserNotes()) {
          logger.warning("PlayerList module requires UserNotes module to be enabled, some features may not work");
        }

        commandManager.registerCommand(injector.getInstance(PlayerListCommand.class));
      }
    }
  }

  private void loadExtensions() {
    if (config.get().modules.isFreeze())
      extensionManager.registerExtension(FreezePlayerExtension.class);

    if (config.get().staffMode.vanishType() == VanishType.INTERNAL_VANISH) {
      extensionManager.registerExtension(InternalVanishExtension.class, VanishExtension.class);
    } else {
      logger.debug("Loading VanishType %s", config.get().staffMode.vanishType());
      if (config.get().staffMode.vanishType() == VanishType.PREMIUM_VANISH ||
          config.get().staffMode.vanishType() == VanishType.SUPER_VANISH
      ) {
        extensionManager.registerExtension(SuperVanishExtension.class, VanishExtension.class);
      }
    }
  }

  public void onDisable() {
    try {
      injector.getInstance(EventMessenger.class).close();
    } catch (Exception e) {
      logger.severe("An error occurred while closing the event messenger, %s", e);
    }

    addonManager.disableAllAddons();
    connection.close();
    loaders.forEach(AbstractLoader::unload);
  }

  public void reload() {
    Stream.of(
        config,
        messengerConfig,
        messagesConfig,
        itemsConfig,
        staffModeBlockedCommands,
        noteMessages
    ).forEach(c -> c.reload().join());

    unregisterListeners();
    loadBukkitListeners();

    loaders.forEach(AbstractLoader::reload);
    addonManager.reloadAllAddons();
  }

  public void registerCommand(Command command) {
    commandManager.registerCommand(command);
  }

  public void unregisterCommand(Command command) {
    commandManager.unregisterCommand(command);
  }

  public String getPrefix() {
    return messagesConfig.get().prefix();
  }
}
