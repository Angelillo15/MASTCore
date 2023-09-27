package com.nookure.mast.api.addons;

import com.nookure.mast.api.addons.annotations.Addon;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;
import java.util.stream.Collectors;

public class AddonsUtils {
  private static Reflections reflections;

  /**
   * Gets all the addons
   *
   * @return A set of all the addons
   */
  public static Set<Class<?>> getAddons(AddonManager manager) {
    return getReflections(manager).getTypesAnnotatedWith(Addon.class);
  }

  /**
   * Gets all the addons that are going to be loaded on scan
   *
   * @return A set of all the addons for the platform
   */
  public static Set<Class<?>> getLoadOnScanAddons(AddonManager manager) {
    return getAddons(manager).stream().filter(c -> {
      Addon addon = c.getAnnotation(Addon.class);
      return !addon.loadOnScan();
    }).collect(Collectors.toSet());
  }

  /**
   * Gets all the addons for a specific platform
   *
   * @param platform The platform {@link Addon.AddonPlatform}
   * @return A set of all the addons for the platform
   */
  public static Set<Class<?>> getAddons(AddonManager manager, Addon.AddonPlatform platform) {
    return getLoadOnScanAddons(manager).stream().filter(c -> {
      Addon addon = c.getAnnotation(Addon.class);
      return addon.platform() == platform;
    }).collect(Collectors.toSet());
  }

  private static Reflections getReflections(AddonManager manager) {
    if (reflections == null) {
      if (manager.getLoaded().toArray() instanceof ClassLoader[] loader) {
        return new Reflections(new ConfigurationBuilder().addClassLoaders(loader));
      }

      throw new RuntimeException("Could not get the class loaders");
    }

    return reflections;
  }
}
