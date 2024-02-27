package com.nookure.staff.paper.bootstrap;

import com.google.inject.TypeLiteral;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.annotation.PluginMessageMessenger;
import com.nookure.staff.api.annotation.RedisPublish;
import com.nookure.staff.api.annotation.RedisSubscribe;
import com.nookure.staff.api.command.CommandManager;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.config.messaging.MessengerConfig;
import com.nookure.staff.api.config.messaging.RedisPartial;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.extension.StaffPlayerExtensionManager;
import com.nookure.staff.api.manager.FreezeManager;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.util.PluginModule;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.api.util.ServerUtils;
import com.nookure.staff.command.sender.ConsoleCommandSender;
import com.nookure.staff.database.PluginConnection;
import com.nookure.staff.messaging.NoneEventManager;
import com.nookure.staff.messaging.RedisMessenger;
import com.nookure.staff.paper.command.PaperCommandManager;
import com.nookure.staff.paper.messaging.BackendMessageMessenger;
import com.nookure.staff.paper.util.PaperScheduler;
import com.nookure.staff.paper.util.PaperServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class PaperPluginModule extends PluginModule {
  private final StaffBootstrapper boot;
  private MessengerConfig.MessengerType messengerType;
  private RedisPartial redisPartial;

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
    bind(Scheduler.class).to(PaperScheduler.class).asEagerSingleton();
    bind(ServerUtils.class).to(PaperServerUtils.class).asEagerSingleton();
    bind(StaffPlayerExtensionManager.class).asEagerSingleton();
    bind(FreezeManager.class).asEagerSingleton();

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

      /*
       * PlayerWrapperManager related area
       */
      bind(new TypeLiteral<PlayerWrapperManager<Player>>() {
      }).toInstance(playerWrapperManager);
      bind(new TypeLiteral<PlayerWrapperManager<?>>() {
      }).toInstance(playerWrapperManager);
    } catch (IOException e) {
      boot.getPLogger().severe("Could not load config");
      throw new RuntimeException(e);
    }

    switch (messengerType) {
      case PM -> bind(EventMessenger.class).to(BackendMessageMessenger.class).asEagerSingleton();
      case REDIS -> {
        bind(Jedis.class).annotatedWith(RedisPublish.class).toInstance(getJedis());
        bind(Jedis.class).annotatedWith(RedisSubscribe.class).toInstance(getJedis());
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
    return config;
  }

  private ConfigurationContainer<ItemsConfig> loadItemConfig() throws IOException {
    return ConfigurationContainer.load(boot.getDataFolder().toPath(), ItemsConfig.class, "items.yml");
  }

  private ConfigurationContainer<BukkitMessages> loadMessages() throws IOException {
    return ConfigurationContainer.load(boot.getDataFolder().toPath(), BukkitMessages.class, "messages.yml");
  }

  private Jedis getJedis() {
    try (Jedis jedis = new Jedis(redisPartial.getAddress(), redisPartial.getPort(), redisPartial.getTimeout(), redisPartial.getPoolSize())) {

      if (!redisPartial.getPassword().isEmpty()) {
        jedis.auth(redisPartial.getPassword());
      }

      jedis.select(redisPartial.getDatabase());

      return jedis;
    } catch (Exception e) {
      boot.getPLogger().severe("Could not connect to Redis");
      throw new RuntimeException(e);
    }
  }

  private ConfigurationContainer<MessengerConfig> loadMessenger() throws IOException {
    ConfigurationContainer<MessengerConfig> config = ConfigurationContainer.load(boot.getDataFolder().toPath(), MessengerConfig.class, "messenger.yml");
    messengerType = config.get().getType();
    redisPartial = config.get().redis;
    return config;
  }
}
