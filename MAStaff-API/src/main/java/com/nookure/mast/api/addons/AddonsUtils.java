package com.nookure.mast.api.addons;

import com.nookure.mast.api.addons.annotations.Addon;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

public class AddonsUtils {
  private static final Reflections reflections = new Reflections("com.nookure");

  /**
   * Gets all the addons
   *
   * @return A set of all the addons
   */
  public static Set<Class<?>> getAddons() {
    return reflections.getTypesAnnotatedWith(Addon.class);
  }

  /**
   * Gets all the addons for a specific platform
   *
   * @param platform The platform {@link Addon.AddonPlatform}
   * @return A set of all the addons for the platform
   */
  public static Set<Class<?>> getAddons(Addon.AddonPlatform platform) {
    return getAddons().stream().filter(c -> {
      Addon addon = c.getAnnotation(Addon.class);
      return addon.platform() == platform || addon.platform() == Addon.AddonPlatform.COMMON;
    }).collect(Collectors.toSet());
  }
}
