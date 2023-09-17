package com.nookure.mast.tests.mocks;

import com.google.inject.Injector;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.api.cmd.Command;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IServerUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MAStaffMock implements MAStaff {
  private Injector injector;

  public void setInjector(Injector injector) {
    this.injector = injector;
  }

  @Override
  public ILogger getPLogger() {
    return MockLogger.getInstance();
  }

  @Override
  public void registerCommand(Command command) {
    getPLogger().info("Registered command " + command);
  }

  @Override
  public boolean isDebug() {
    return true;
  }

  @Override
  public void setDebug(boolean debug) {
    getPLogger().info("Debug mode set to " + debug);
  }

  @Override
  public IServerUtils getServerUtils() {
    return null;
  }

  @Override
  public File getPluginDataFolder() {
    try {
      return File.createTempFile("MAStaff", "test");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public InputStream getPluginResource(String s) {
    return null;
  }

  @Override
  public Injector getInjector() {
    return injector;
  }

  @Override
  public void registerListeners() {
    getPLogger().info("Registered listeners");
  }

  @Override
  public void registerCommands() {
    getPLogger().info("Registered commands");
  }

  @Override
  public void loadConfig() {
    getPLogger().info("Loaded config");
  }

  @Override
  public void drawLogo() {
    getPLogger().info("Drawn logo");
  }

  @Override
  public void loadDatabase() {
    getPLogger().info("Loaded database");
  }

  @Override
  public void loadModules() {
    getPLogger().info("Loaded modules");
  }

  @Override
  public void unregisterCommands() {
    getPLogger().info("Unregistered commands");
  }

  @Override
  public void unregisterListeners() {
    getPLogger().info("Unregistered listeners");
  }

  @Override
  public void unloadDatabase() {
    getPLogger().info("Unloaded database");
  }

  @Override
  public void reload() {
    getPLogger().info("Reloaded");
  }

  @Override
  public Addon.AddonPlatform getPlatform() {
    return Addon.AddonPlatform.COMMON;
  }
}
