package com.nookure.staff.paper.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.Constants;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaffPlatform;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.paper.NookureStaff;
import com.nookure.staff.paper.util.PaperLoggerImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;

public class StaffBootstrapper extends JavaPlugin implements NookureStaffPlatform<JavaPlugin> {
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  private boolean debug = false;
  private Injector injector;
  private Logger logger;
  private NookureStaff plugin;

  @Override
  public void onEnable() {
    Bukkit.getConsoleSender().sendMessage(Component.text("""
         
         ▐ ▄             ▄ •▄ ▄• ▄▌▄▄▄  ▄▄▄ .    .▄▄ · ▄▄▄▄▄ ▄▄▄· ·▄▄▄·▄▄▄
        •█▌▐█▪     ▪     █▌▄▌▪█▪██▌▀▄ █·▀▄.▀·    ▐█ ▀. •██  ▐█ ▀█ ▐▄▄·▐▄▄·
        ▐█▐▐▌ ▄█▀▄  ▄█▀▄ ▐▀▀▄·█▌▐█▌▐▀▀▄ ▐▀▀▪▄    ▄▀▀▀█▄ ▐█.▪▄█▀▀█ ██▪ ██▪
        ██▐█▌▐█▌.▐▌▐█▌.▐▌▐█.█▌▐█▄█▌▐█•█▌▐█▄▄▌    ▐█▄▪▐█ ▐█▌·▐█ ▪▐▌██▌.██▌.
        ▀▀ █▪ ▀█▄▀▪ ▀█▄▀▪·▀  ▀ ▀▀▀ .▀  ▀ ▀▀▀      ▀▀▀▀  ▀▀▀  ▀  ▀ ▀▀▀ ▀▀▀
        """).color(NamedTextColor.LIGHT_PURPLE)
    );

    Bukkit.getConsoleSender().sendMessage(
        Component.text("NookureStaff v" + Constants.VERSION + " by Angelillo15").color(NamedTextColor.LIGHT_PURPLE)
    );

    loadLogger();
    loadInjector();
    loadPlugin();

    plugin.onEnable();
  }

  @Override
  public void onDisable() {
    plugin.onDisable();
  }

  public void loadLogger() {
    logger = new PaperLoggerImpl(this);
  }

  public void loadInjector() {
    injector = Guice.createInjector(new PaperPluginModule(this));
    injector.injectMembers(this);
  }

  public void loadPlugin() {
    logger.info("💉  Injecting plugin...");
    plugin = injector.getInstance(NookureStaff.class);
    logger.info("💉  Plugin injected!");
  }

  @Override
  public JavaPlugin getPlatform() {
    return this;
  }

  @Override
  public Logger getPLogger() {
    return logger;
  }

  @Override
  public boolean isDebug() {
    return debug;
  }

  @Override
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  @Override
  public void reload() {
    plugin.reload();
  }

  @Override
  public File getPluginDataFolder() {
    return getDataFolder();
  }

  @Override
  public InputStream getPluginResource(String s) {
    return getResource(s);
  }

  @Override
  public Injector getInjector() {
    return injector;
  }

  @Override
  public String getPrefix() {
    return messages.get().prefix();
  }
}
