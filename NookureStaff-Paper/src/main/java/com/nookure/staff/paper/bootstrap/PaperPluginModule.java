package com.nookure.staff.paper.bootstrap;

import com.google.inject.TypeLiteral;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.command.CommandManager;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.ItemsConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.util.PluginModule;
import com.nookure.staff.command.sender.ConsoleCommandSender;
import com.nookure.staff.database.PluginConnection;
import com.nookure.staff.paper.command.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class PaperPluginModule extends PluginModule {
  private final StaffBootstrapper boot;

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
}
