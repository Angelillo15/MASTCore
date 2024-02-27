package com.nookure.staff.api.extension;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.stream.Stream;

@Singleton
public class StaffPlayerExtensionManager {
  private final ArrayList<Class<? extends StaffPlayerExtension>> extensions = new ArrayList<>();

  public void registerExtension(Class<? extends StaffPlayerExtension> extension) {
    extensions.add(extension);
  }

  public Stream<Class<? extends StaffPlayerExtension>> getExtensionsStream() {
    return extensions.stream();
  }

  public void clearExtensions() {
    extensions.clear();
  }
}
