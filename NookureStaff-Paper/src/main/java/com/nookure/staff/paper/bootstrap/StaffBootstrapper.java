package com.nookure.staff.paper.bootstrap;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.LibraryManager;
import com.alessiodp.libby.PaperLibraryManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nookure.staff.Constants;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaffPlatform;
import com.nookure.staff.api.command.Command;
import com.nookure.staff.lib.DefaultLibRepo;
import com.nookure.staff.paper.NookureStaff;
import com.nookure.staff.paper.util.Metrics;
import com.nookure.staff.paper.util.PaperLoggerImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;

public class StaffBootstrapper extends JavaPlugin implements NookureStaffPlatform<JavaPlugin> {
  private boolean debug = false;
  private Injector injector;
  private Logger logger;
  private NookureStaff plugin;

  @Override
  public void onEnable() {
    checkMAStaff();

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

    loadDependencies();

    loadLogger();
    loadInjector();
    loadPlugin();
    new Metrics(this, 16548);

    plugin.onEnable();
  }

  public void checkMAStaff() {
    if (Bukkit.getPluginManager().getPlugin("MAStaff") != null) {
      Bukkit.getConsoleSender().sendMessage(Component.text("MAStaff detected!").color(NamedTextColor.RED));
      Bukkit.getConsoleSender().sendMessage(Component.text("Please remove it to avoid conflicts.").color(NamedTextColor.RED));
      Bukkit.getConsoleSender().sendMessage(Component.text("Disabling NookureStaff...").color(NamedTextColor.RED));
      Bukkit.getPluginManager().disablePlugin(this);
    }
  }

  private void loadDependencies() {
    LibraryManager manager;

    try {
      Class.forName("com.nookure.staff.paper.bootstrap.PaperPluginModule");
      manager = new PaperLibraryManager(this);
    } catch (ClassNotFoundException e) {
      manager = new BukkitLibraryManager(this);
    }

    manager.addMavenCentral();
    manager.addJitPack();

    DefaultLibRepo.getInstance().getLibraries().forEach(manager::loadLibrary);
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
    return plugin.getPrefix();
  }

  @Override
  public void registerCommand(Command command) {
    plugin.registerCommand(command);
  }

  @Override
  public void unregisterCommand(Command command) {
    plugin.unregisterCommand(command);
  }
}
