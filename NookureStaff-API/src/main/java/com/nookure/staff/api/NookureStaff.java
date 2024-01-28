package com.nookure.staff.api;

import com.google.inject.Injector;

import java.io.File;
import java.io.InputStream;

public interface NookureStaff {
  Logger getPLogger();

  boolean isDebug();

  void setDebug(boolean debug);

  void reload();

  File getPluginDataFolder();

  InputStream getPluginResource(String s);

  Injector getInjector();
}
