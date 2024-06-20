package com.nookure.staff.bungeecord;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaffPlatform;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.messaging.Channels;
import com.nookure.staff.bungeecord.messaging.PluginMessageRouter;
import com.nookure.staff.bungeecord.module.WaterfallLogger;
import com.nookure.staff.bungeecord.module.WaterfallPluginModule;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.InputStream;

public class NookureStaff extends Plugin implements NookureStaffPlatform<Plugin> {
  private Injector injector;
  private Logger logger;

  public void onEnable() {
    logger = new WaterfallLogger(this);
    this.injector = Guice.createInjector(new WaterfallPluginModule(this));
    injector.injectMembers(this);

    EventManager eventManager = injector.getInstance(EventManager.class);

    ProxyServer.getInstance().registerChannel(Channels.EVENTS);
    ProxyServer.getInstance().getPluginManager().registerListener(this, injector.getInstance(PluginMessageRouter.class));

    eventManager.registerListener(injector.getInstance(PluginMessageRouter.class));

    logger.info("NookureStaff waterfall bridge has been enabled!.");
    logger.debug("Debug mode is enabled.");
  }

  @Override
  public Logger getPLogger() {
    return logger;
  }

  @Override
  public boolean isDebug() {
    return System.getProperty("nookure.debug") != null;
  }

  @Override
  public void setDebug(boolean debug) {

  }

  @Override
  public void reload() {

  }

  @Override
  public File getPluginDataFolder() {
    return getDataFolder();
  }

  @Override
  public InputStream getPluginResource(String s) {
    return getResourceAsStream(s);
  }

  @Override
  public Injector getInjector() {
    return injector;
  }

  @Override
  public String getPrefix() {
    return "NookureStaff";
  }

  @Override
  public Plugin getPlatform() {
    return this;
  }
}
