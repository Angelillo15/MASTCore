package com.nookure.staff.paper.data;

import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.IOException;

public class PluginObjectInputStream extends BukkitObjectInputStream {
  private final static Class<?> wrapper;

  static {
    try {
      wrapper = Class.forName("org.bukkit.util.io.Wrapper");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  protected PluginObjectInputStream() throws IOException, SecurityException {
  }

  protected PluginObjectInputStream(java.io.InputStream in) throws IOException {
    super(in);
  }

  @Override
  protected Object resolveObject(Object obj) throws IOException {
    return super.resolveObject(obj);
  }
}
