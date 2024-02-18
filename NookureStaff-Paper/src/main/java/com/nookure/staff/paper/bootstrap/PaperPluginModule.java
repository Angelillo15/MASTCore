package com.nookure.staff.paper.bootstrap;

import com.google.inject.TypeLiteral;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.command.CommandManager;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.config.messaging.MessengerConfig;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.util.PluginModule;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.command.sender.ConsoleCommandSender;
import com.nookure.staff.database.PluginConnection;
import com.nookure.staff.paper.command.PaperCommandManager;
import com.nookure.staff.paper.messaging.BackendMessageMessenger;
import com.nookure.staff.paper.util.PaperScheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class PaperPluginModule extends PluginModule {
  private final StaffBootstrapper boot;
  private MessengerConfig.MessengerType messengerType;

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
      case REDIS -> throw new RuntimeException("Redis not supported yet");
      default -> throw new RuntimeException("Unknown messenger type");
    }
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

  private ConfigurationContainer<MessengerConfig> loadMessenger() throws IOException {
    ConfigurationContainer<MessengerConfig> config = ConfigurationContainer.load(boot.getDataFolder().toPath(), MessengerConfig.class, "messenger.yml");
    messengerType = config.get().getType();
    return config;
  }
}
