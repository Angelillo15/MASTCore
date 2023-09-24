package com.nookure.mast.addon;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.addons.*;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.api.cmd.Command;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.addons.AddonDisableEvent;
import com.nookure.mast.api.event.addons.AddonEnableEvent;
import com.nookure.mast.api.event.addons.AddonReloadEvent;
import com.nookure.mast.api.exceptions.AddonException;
import es.angelillo15.mast.api.ILogger;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Singleton
public class ServerAddonManager implements AddonManager {
  @Inject
  private MAStaff instance;
  @Inject
  private ILogger logger;
  @Inject
  private EventManager eventManager;
  private final Map<String, AddonContainer> addonsById = new LinkedHashMap<>();
  private final Map<Object, AddonContainer> addonsInstances = new IdentityHashMap<>();
  private final Map<AddonContainer, List<Object>> listeners = new HashMap<>();
  private final ArrayList<URLClassLoader> loaded = new ArrayList<>();

  private void registerAddon(AddonContainer addonContainer) {
    addonsById.put(addonContainer.getDescription().getID(), addonContainer);
    addonsInstances.put(addonContainer.getInstance(), addonContainer);
  }

  @Override
  public Optional<AddonContainer> fromInstance(Object instance) {
    return Optional.ofNullable(addonsInstances.get(instance));
  }

  @Override
  public Optional<AddonContainer> getAddon(String id) {
    return Optional.ofNullable(addonsById.get(id));
  }

  @Override
  public Collection<AddonContainer> getAddons() {
    return addonsById.values();
  }

  @Override
  public boolean isAddonEnabled(String id) {
    return getAddon(id).isPresent() && getAddon(id).get().getStatus() == AddonStatus.ENABLED;
  }

  @Override
  public void loadAddonsToClasspath(Path directory) throws IOException {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory,
        p -> p.toFile().isFile() && p.toString().endsWith(".jar"))) {
      for (Path path : stream) {
        URL[] urls = new URL[]{path.toUri().toURL()};
        URLClassLoader loader = new URLClassLoader(urls, instance.getClass().getClassLoader());
        loaded.add(loader);
        logger.debug("Addon " + path.getFileName() + " added to classpath");
      }
    }
  }

  @Override
  public void enableAllAddonsFromTheClasspath() {
    AddonsUtils.getAddons(instance.getPlatform()).forEach(this::enableAddon);
  }

  @Override
  public void enableAddon(Class<?> addonClasss) {

    AddonContainer addonContainer = new ServerAddonContainer(new AddonDescriptionBuilder()
        .setAddon(addonClasss.getAnnotation(Addon.class))
        .build()
    );

    logger.debug("Enabling addon " + addonContainer.getDescription().getID());

    Injector addonInjector = instance.getInjector().createChildInjector(
        new AddonCommonModule(addonContainer, instance, addonClasss)
    );

    Object addon = addonInjector.getInstance(addonClasss);

    addonContainer.setInstance(addon);
    addonContainer.setStatus(AddonStatus.ENABLED);

    registerAddon(addonContainer);

    for (Class<?> listener : addonContainer.getAddon().listeners()) {
      registerListener(addonInjector.getInstance(listener), addonContainer);
    }

    for (Class<? extends Command> commands : addonContainer.getAddon().commands()) {
      registerCommand(addonInjector.getInstance(commands), addonContainer);
    }

    if (addon instanceof AddonActions actions) actions.onEnable();

    eventManager.fireEvent(new AddonEnableEvent(addonContainer));
  }

  @Override
  public void enableAllAddons() {
    enableAllAddonsFromTheClasspath();
  }

  @Override
  public void disableAddon(Object addon) {
    fromInstance(addon).ifPresent(addonContainer -> {
      addonContainer.setStatus(AddonStatus.DISABLED);
      addonsById.remove(addonContainer.getDescription().getID());
      addonsInstances.remove(addonContainer.getInstance());
      unregisterListeners(addonContainer);

      if (addon instanceof AddonActions actions) actions.onDisable();

      eventManager.fireEvent(new AddonDisableEvent(addonContainer));
    });
  }

  @Override
  public void disableAllAddons() {
    addonsById.values().forEach(addonContainer -> disableAddon(addonContainer.getInstance()));
  }

  @Override
  public void reloadAddon(Object addon) {
    fromInstance(addon).ifPresent(addonContainer -> {
      if (addon instanceof AddonActions actions) actions.onReload();
      eventManager.fireEvent(new AddonReloadEvent(addonContainer));
    });
  }

  @Override
  public void reloadAllAddons() {
    getAddons().forEach(addonContainer -> reloadAddon(addonContainer.getInstance()));
  }

  @Override
  public void destroyClassloader() {
    disableAllAddons();
    loaded.forEach(loader -> {
      try {
        loader.close();
      } catch (IOException e) {
        throw new AddonException("Error occurred while destroying addons ClassLoaders", e);
      }
    });
  }

  @Override
  public void registerListener(Object listener, AddonContainer addon) {
    if (!listeners.containsKey(addon)) listeners.put(addon, new ArrayList<>());
    logger.debug("Registering listener " + listener.getClass().getSimpleName() + " for addon " + addon.getDescription().getID());
    listeners.get(addon).add(listener);
    eventManager.registerListener(listener);
  }

  @Override
  public void registerListeners(Class<?> listener, AddonContainer addon) {
    registerListener(instance.getInjector().getInstance(listener), addon);
  }

  @Override
  public void unregisterListener(Object listener, AddonContainer addon) {
    if (listeners.containsKey(addon)) {
      listeners.get(addon).remove(listener);
      eventManager.unregisterListener(listener);
    }
  }

  @Override
  public void unregisterListeners(AddonContainer addon) {
    if (listeners.containsKey(addon)) {
      listeners.get(addon).forEach(listener -> eventManager.unregisterListener(listener));
      listeners.remove(addon);
    }
  }

  @Override
  public void registerCommand(Command command, AddonContainer addon) {
    instance.registerCommand(command);
  }
}
