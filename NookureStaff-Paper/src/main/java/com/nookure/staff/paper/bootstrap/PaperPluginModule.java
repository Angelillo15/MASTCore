package com.nookure.staff.paper.bootstrap;

import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.addons.AddonManager;
import com.nookure.staff.api.annotation.PluginMessageMessenger;
import com.nookure.staff.api.command.CommandManager;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.config.bukkit.StaffModeBlockedCommands;
import com.nookure.staff.api.config.bukkit.partials.messages.note.NoteMessages;
import com.nookure.staff.api.config.common.CommandConfig;
import com.nookure.staff.api.config.messaging.MessengerConfig;
import com.nookure.staff.api.config.messaging.RedisPartial;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.database.repository.StaffStateRepository;
import com.nookure.staff.api.extension.StaffPlayerExtensionManager;
import com.nookure.staff.api.manager.FreezeManager;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.placeholder.PlaceholderManager;
import com.nookure.staff.api.service.PinUserService;
import com.nookure.staff.api.service.UserNoteService;
import com.nookure.staff.api.state.PlayerState;
import com.nookure.staff.api.util.transformer.NameTagTransformer;
import com.nookure.staff.api.util.transformer.PlayerTransformer;
import com.nookure.staff.api.util.PluginModule;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.api.util.ServerUtils;
import com.nookure.staff.command.sender.ConsoleCommandSender;
import com.nookure.staff.database.PluginConnection;
import com.nookure.staff.database.repository.SQLStaffStateRepository;
import com.nookure.staff.messaging.NoneEventManager;
import com.nookure.staff.messaging.RedisMessenger;
import com.nookure.staff.paper.PaperPlayerWrapper;
import com.nookure.staff.paper.StaffPaperPlayerWrapper;
import com.nookure.staff.paper.addon.ServerAddonManager;
import com.nookure.staff.paper.command.PaperCommandManager;
import com.nookure.staff.paper.factory.PaperPlayerWrapperFactory;
import com.nookure.staff.paper.factory.StaffPaperPlayerWrapperFactory;
import com.nookure.staff.paper.messaging.BackendMessageMessenger;
import com.nookure.staff.paper.util.MockScheduler;
import com.nookure.staff.paper.util.transoformer.PaperPlayerTransformer;
import com.nookure.staff.paper.util.PaperScheduler;
import com.nookure.staff.paper.util.PaperServerUtils;
import com.nookure.staff.paper.util.transoformer.nametag.DummyNameTagTransformer;
import com.nookure.staff.paper.util.transoformer.nametag.TabNameTagTransformer;
import com.nookure.staff.service.PinUserServiceImpl;
import com.nookure.staff.service.UserNoteServiceImpl;
import io.ebean.Database;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PaperPluginModule extends PluginModule {
  private final StaffBootstrapper boot;
  private MessengerConfig.MessengerType messengerType;
  private RedisPartial redisPartial;
  private ConfigurationContainer<BukkitConfig> config;

  public PaperPluginModule(StaffBootstrapper boot) {
    this.boot = boot;
  }

  @Override
  protected void configure() {
    PlayerWrapperManager<Player> playerWrapperManager = new PlayerWrapperManager<>();

    super.configure();
    bind(JavaPlugin.class).toInstance(boot);
    bind(Logger.class).toInstance(boot.getPLogger());
    bind(NookureStaff.class).toInstance(boot);
    bind(com.nookure.staff.paper.NookureStaff.class).asEagerSingleton();
    bind(CommandMap.class).toInstance(getCommandMap());
    bind(AbstractPluginConnection.class).to(PluginConnection.class).asEagerSingleton();
    bind(StaffItemsManager.class).asEagerSingleton();
    bind(ConsoleCommandSender.class).asEagerSingleton();
    bind(CommandManager.class).to(PaperCommandManager.class).asEagerSingleton();
    if (StaffBootstrapper.isMock) {
      bind(Scheduler.class).to(MockScheduler.class).asEagerSingleton();
    } else {
      bind(Scheduler.class).to(PaperScheduler.class).asEagerSingleton();
    }
    bind(ServerUtils.class).to(PaperServerUtils.class).asEagerSingleton();
    bind(StaffPlayerExtensionManager.class).asEagerSingleton();
    bind(FreezeManager.class).asEagerSingleton();
    bind(PlaceholderManager.class).asEagerSingleton();
    bind(PlayerTransformer.class).to(PaperPlayerTransformer.class).asEagerSingleton();

    bind(AddonManager.class)
        .to(ServerAddonManager.class)
        .asEagerSingleton();

    bind(UserNoteService.class)
        .to(UserNoteServiceImpl.class)
        .asEagerSingleton();

    bind(PinUserService.class)
        .to(PinUserServiceImpl.class)
        .asEagerSingleton();

    bind(StaffStateRepository.class)
        .to(SQLStaffStateRepository.class)
        .asEagerSingleton();

    install(new FactoryModuleBuilder()
        .implement(PaperPlayerWrapper.class, PaperPlayerWrapper.class)
        .build(PaperPlayerWrapperFactory.class)
    );

    install(new FactoryModuleBuilder()
        .implement(StaffPaperPlayerWrapper.class, StaffPaperPlayerWrapper.class)
        .build(StaffPaperPlayerWrapperFactory.class)
    );

    try {
      /*
       * Configuration related area
       */
      bind(new TypeLiteral<ConfigurationContainer<BukkitConfig>>() {
      }).toInstance(loadBukkitConfig());
      bind(new TypeLiteral<ConfigurationContainer<ItemsConfig>>() {
      }).toInstance(loadItemConfig());
      bind(new TypeLiteral<ConfigurationContainer<BukkitMessages>>() {
      }).toInstance(loadMessages());
      bind(new TypeLiteral<ConfigurationContainer<MessengerConfig>>() {
      }).toInstance(loadMessenger());
      bind(new TypeLiteral<ConfigurationContainer<CommandConfig>>() {
      }).toInstance(loadCommands());
      bind(new TypeLiteral<ConfigurationContainer<NoteMessages>>() {
      }).toInstance(loadNoteMessages());
      bind(new TypeLiteral<ConfigurationContainer<StaffModeBlockedCommands>>() {
      }).toInstance(loadStaffModeBlockedCommands());

      /*
       * PlayerWrapperManager related area
       */
      bind(new TypeLiteral<PlayerWrapperManager<Player>>() {
      }).toInstance(playerWrapperManager);
      bind(new TypeLiteral<PlayerWrapperManager<?>>() {
      }).toInstance(playerWrapperManager);
      bind(new TypeLiteral<List<Class<? extends PlayerState>>>() {
      }).toInstance(new ArrayList<>());

      /*
       * AtomicReference related area
       */
      bind(new TypeLiteral<AtomicReference<Database>>() {
      }).toInstance(new AtomicReference<>(null));
      bind(new TypeLiteral<AtomicReference<DataSource>>() {
      }).toInstance(new AtomicReference<>(null));
      bind(new TypeLiteral<AtomicReference<PaperNookureInventoryEngine>>() {
      }).toInstance(new AtomicReference<>(null));
    } catch (IOException e) {
      boot.getPLogger().severe("Could not load config");
      throw new RuntimeException(e);
    }

    switch (messengerType) {
      case PM -> bind(EventMessenger.class).to(BackendMessageMessenger.class).asEagerSingleton();
      case REDIS -> {
        bind(JedisPool.class).toInstance(getJedisPool());
        boot.getPLogger().info("<green>Successfully connected to Redis");
        bind(EventMessenger.class).to(RedisMessenger.class).asEagerSingleton();
      }
      case NONE -> bind(EventMessenger.class).to(NoneEventManager.class).asEagerSingleton();
      default -> throw new RuntimeException("Unknown messenger type");
    }

    if (messengerType != MessengerConfig.MessengerType.NONE)
      bind(EventMessenger.class).annotatedWith(PluginMessageMessenger.class).to(BackendMessageMessenger.class).asEagerSingleton();
    else
      bind(EventMessenger.class).annotatedWith(PluginMessageMessenger.class).to(NoneEventManager.class).asEagerSingleton();

    loadNameTagTransformer();
  }

  private CommandMap getCommandMap() {
    try {
      return (CommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
    } catch (Exception e) {
      throw new RuntimeException("Could not get CommandMap", e);
    }
  }

  private ConfigurationContainer<BukkitConfig> loadBukkitConfig() throws IOException {
    ConfigurationContainer<BukkitConfig> config = ConfigurationContainer.load(boot.getDataFolder().toPath(), BukkitConfig.class);
    boot.setDebug(config.get().isDebug());
    this.config = config;
    return config;
  }

  private ConfigurationContainer<ItemsConfig> loadItemConfig() throws IOException {
    return ConfigurationContainer.load(boot.getDataFolder().toPath(), ItemsConfig.class, "items.yml");
  }

  private ConfigurationContainer<BukkitMessages> loadMessages() throws IOException {
    return ConfigurationContainer.load(boot.getDataFolder().toPath(), BukkitMessages.class, "messages.yml");
  }

  private ConfigurationContainer<CommandConfig> loadCommands() throws IOException {
    return ConfigurationContainer.load(boot.getDataFolder().toPath(), CommandConfig.class, "commands.yml");
  }

  private JedisPool getJedisPool() {
    return getJedisPool(
        redisPartial.getAddress(),
        redisPartial.getPort(),
        redisPartial.getTimeout(),
        redisPartial.getPoolSize(),
        redisPartial.getDatabase(),
        redisPartial.getUsername(),
        redisPartial.getPassword()
    );
  }

  private JedisPool getJedisPool(
      @NotNull final String address,
      final int port,
      final int timeout,
      final int poolSize,
      final int database,
      @NotNull final String username,
      @NotNull final String password
  ) {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(poolSize);

    if (username.isEmpty() && password.isEmpty()) {
      return new JedisPool(poolConfig, address, port, timeout, null, database);
    } else if (username.isEmpty()) {
      return new JedisPool(poolConfig, address, port, timeout, password, database);
    } else {
      return new JedisPool(poolConfig, address, port, timeout, username, password, database);
    }
  }

  private void loadNameTagTransformer() {
    if (!config.get().glow.tabIntegration) {
      bind(NameTagTransformer.class).to(DummyNameTagTransformer.class).asEagerSingleton();
      return;
    }

    try {
      Class.forName("me.neznamy.tab.api.TabAPI");
      bind(NameTagTransformer.class).to(TabNameTagTransformer.class).asEagerSingleton();
    } catch (ClassNotFoundException e) {
      bind(NameTagTransformer.class).to(DummyNameTagTransformer.class).asEagerSingleton();
    }
  }

  private ConfigurationContainer<MessengerConfig> loadMessenger() throws IOException {
    ConfigurationContainer<MessengerConfig> config = ConfigurationContainer.load(boot.getDataFolder().toPath(), MessengerConfig.class, "messenger.yml");
    messengerType = config.get().getType();
    redisPartial = config.get().redis;
    return config;
  }

  private ConfigurationContainer<NoteMessages> loadNoteMessages() throws IOException {
    return ConfigurationContainer.load(boot.getDataFolder().toPath(), NoteMessages.class, "partial/note-messages.yml");
  }

  private ConfigurationContainer<StaffModeBlockedCommands> loadStaffModeBlockedCommands() throws IOException {
    return ConfigurationContainer.load(boot.getDataFolder().toPath(), StaffModeBlockedCommands.class, "staff-mode-blocked-commands.yml");
  }
}
