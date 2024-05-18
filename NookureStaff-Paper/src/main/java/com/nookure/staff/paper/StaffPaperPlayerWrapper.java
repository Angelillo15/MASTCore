package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.event.server.BroadcastMessageExcept;
import com.nookure.staff.api.event.staff.StaffModeDisabledEvent;
import com.nookure.staff.api.event.staff.StaffModeEnabledEvent;
import com.nookure.staff.api.extension.StaffPlayerExtension;
import com.nookure.staff.api.extension.StaffPlayerExtensionManager;
import com.nookure.staff.api.item.StaffItem;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.model.StaffDataModel;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.paper.data.StaffModeData;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class StaffPaperPlayerWrapper extends PaperPlayerWrapper implements StaffPlayerWrapper {
  @Inject
  private NookureStaff plugin;
  @Inject
  private JavaPlugin javaPlugin;
  @Inject
  private Logger logger;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  @Inject
  private ConfigurationContainer<BukkitConfig> config;
  @Inject
  private StaffItemsManager itemsManager;
  @Inject
  private AbstractPluginConnection connection;
  @Inject
  private Scheduler scheduler;
  @Inject
  private EventMessenger eventMessenger;
  @Inject
  private StaffPlayerExtensionManager extensionManager;
  @Inject
  private Injector injector;
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  private final Map<Class<? extends StaffPlayerExtension>, StaffPlayerExtension> extensionMap = new HashMap<>();
  private final Map<Integer, StaffItem> items = new HashMap<>();
  private StaffDataModel staffDataModel;
  private boolean staffMode = false;
  private boolean vanish = false;
  private boolean staffChatAsDefault = false;
  private StaffModeData staffModeData;

  @Override
  public void toggleStaffMode(boolean silentJoin) {
    if (!staffMode) enableStaffMode(silentJoin);
    else disableStaffMode();
  }

  @Override
  public boolean isInStaffMode() {
    return staffMode;
  }

  @Override
  public void toggleVanish() {
    if (!config.get().modules.isVanish()) {
      player.performCommand("vanish");
    }

    writeVanishState(!vanish);

    if (vanish) enableVanish(false);
    else disableVanish(false);
  }

  @Override
  public void enableVanish(boolean silent) {
    if (!vanish) return;

    logger.debug("Enabling vanish for %s", player.getName());

    if (!silent) {
      sendMiniMessage(messages.get().vanish.vanishEnabled());

      playerWrapperManager.stream()
          .forEach(p ->
              p.sendMiniMessage(messages.get().vanish.vanishEnabledBroadcast(), "player", player.getName())
          );
    }

    Bukkit.getOnlinePlayers().stream()
        .filter(p -> !p.hasPermission(Permissions.STAFF_VANISH_SEE))
        .forEach(p -> p.hidePlayer(javaPlugin, player));
  }

  @Override
  public void disableVanish(boolean silent) {
    if (vanish) return;

    logger.debug("Disabling vanish for %s", player.getName());

    if (!silent) {
      sendMiniMessage(messages.get().vanish.vanishDisabled());

      playerWrapperManager.stream()
          .forEach(p ->
              p.sendMiniMessage(messages.get().vanish.vanishDisabledBroadcast(), "player", player.getName())
          );
    }

    Bukkit.getOnlinePlayers()
        .forEach(p -> p.showPlayer(javaPlugin, player));
  }

  @Override
  public boolean isInVanish() {
    return vanish;
  }

  @Override
  public void setItems() {
    if (items.isEmpty()) {
      itemsManager.getItems().forEach((identifier, item) -> {
        if (item.getPermission() != null && !player.hasPermission(item.getPermission())) return;

        items.put(item.getSlot(), item);
      });
    }

    items.forEach((identifier, item) -> item.setItem(player));
  }

  @Override
  public @NotNull Map<Integer, StaffItem> getItems() {
    return items;
  }

  private void enableStaffMode(boolean silentJoin) {
    long time = System.currentTimeMillis();

    enablePlayerPerks();

    if (!silentJoin) {
      saveLocation();
      saveInventory();
    }

    setItems();
    sendMiniMessage(messages.get().staffMode.toggledOn());
    writeStaffModeState(true);

    if (config.get().staffMode.enableVanishOnStaffEnable()) {
      writeVanishState(true);
      enableVanish(true);
    }

    eventMessenger.publish(this, new StaffModeEnabledEvent(getUniqueId()));
    eventMessenger.publish(this, new BroadcastMessageExcept(messages.get().staffMode.toggledOnOthers()
        .replace("{player}", player.getName()),
        getUniqueId())
    );

    try {
      extensionMap.values().forEach(StaffPlayerExtension::onStaffModeEnabled);
    } catch (Exception e) {
      logger.severe("An error occurred while enabling staff mode for %s: %s", player.getName(), e.getMessage());
    }

    logger.debug("Staff mode enabled for %s in %dms", player.getName(), System.currentTimeMillis() - time);
  }

  private void disableStaffMode() {
    long time = System.currentTimeMillis();
    disablePlayerPerks();
    restoreInventory();
    if (config.get().staffMode.teleportToPreviousLocation()) loadPreviousLocation();
    sendMiniMessage(messages.get().staffMode.toggledOff());
    writeStaffModeState(false);

    if (config.get().staffMode.disableVanishOnStaffDisable()) {
      writeVanishState(false);
      disableVanish(true);
    }

    eventMessenger.publish(this, new StaffModeDisabledEvent(getUniqueId()));
    eventMessenger.publish(this, new BroadcastMessageExcept(messages.get().staffMode.toggledOffOthers()
        .replace("{player}", player.getName()),
        getUniqueId())
    );

    try {
      extensionMap.values().forEach(StaffPlayerExtension::onStaffModeDisabled);
    } catch (Exception e) {
      logger.severe("An error occurred while enabling staff mode for %s: %s", player.getName(), e.getMessage());
    }

    logger.debug("Staff mode disabled for %s in %dms", player.getName(), System.currentTimeMillis() - time);
  }

  public void enablePlayerPerks() {
    player.setAllowFlight(true);
    player.setFlying(true);
    player.setInvulnerable(true);
    loadPotionEffects();
  }

  public void disablePlayerPerks() {
    player.setAllowFlight(false);
    player.setFlying(false);
    player.setInvulnerable(false);
    unloadPotionEffects();

    if (player.getGameMode() == GameMode.CREATIVE) {
      player.setFoodLevel(20);
    }
  }

  @Override
  public void saveInventory() {
    if (staffModeData == null) {
      staffModeData = StaffModeData.read(plugin, this);
    }

    assert staffModeData != null;

    staffModeData.record().playerInventory(player.getInventory().getContents());
    staffModeData.record().playerInventoryArmor(player.getInventory().getArmorContents());

    staffModeData.write();

    clearInventory();
  }

  public void saveLocation() {
    staffModeData.record().enabledLocation(player.getLocation());
    staffModeData.write();
  }

  public void loadPreviousLocation() {
    Location location = staffModeData.record().enabledLocation();
    if (location == null) return;
    player.teleport(location);
  }

  private void writeVanishState(boolean state) {
    scheduler.async(() -> {
      StaffDataModel staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());

      staffDataModel.setVanished(state);

      try {
        connection.getStorm().save(staffDataModel);
      } catch (SQLException e) {
        logger.severe("An error occurred while saving vanish state for %s: %s", player.getName(), e.getMessage());
      }

      logger.debug("Vanish state for %s has been set to %s on the database", player.getName(), state);
    });

    vanish = state;
  }

  private void writeStaffModeState(boolean state) {
    scheduler.async(() -> {
      StaffDataModel staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());

      staffDataModel.setStaffMode(state);

      try {
        connection.getStorm().save(staffDataModel);
      } catch (SQLException e) {
        logger.severe("An error occurred while saving staff mode state for %s: %s", player.getName(), e.getMessage());
      }

      logger.debug("Staff mode state for %s has been set to %s on the database", player.getName(), state);
    });

    staffModeData.record().staffMode(state);
    staffModeData.write();

    staffMode = state;
  }

  @Override
  public void clearInventory() {
    player.getInventory().clear();
    player.getInventory().setArmorContents(null);
  }

  @Override
  public void restoreInventory() {
    if (staffModeData == null) {
      staffModeData = StaffModeData.read(plugin, this);
    }

    assert staffModeData != null;

    player.getInventory().setContents(staffModeData.record().playerInventory());
    player.getInventory().setArmorContents(staffModeData.record().playerInventoryArmor());

    staffModeData.write();
  }

  @Override
  public boolean isStaffChatAsDefault() {
    return staffChatAsDefault;
  }

  @Override
  public void setStaffChatAsDefault(boolean staffChatAsDefault) {
    this.staffChatAsDefault = staffChatAsDefault;

    StaffDataModel staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());

    staffDataModel.setStaffChatEnabled(staffChatAsDefault);

    scheduler.async(() -> {
      try {
        connection.getStorm().save(staffDataModel);
      } catch (SQLException e) {
        logger.severe("An error occurred while saving staff chat state for %s: %s", player.getName(), e.getMessage());
      }
    });
  }

  private void checkStaffModeState() {
    if (staffModeData == null) {
      staffModeData = StaffModeData.read(plugin, this);
    }

    assert staffModeData != null;

    if (staffModeData.record().staffMode()) {
      clearInventory();
      enableStaffMode(true);
      return;
    }

    if (staffDataModel == null) {
      staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());
    }

    if (staffDataModel.isStaffMode()) {
      saveInventory();
      saveLocation();
      enableStaffMode(true);
    }

    staffChatAsDefault = staffDataModel.isStaffChatEnabled();
  }

  @Override
  public void toggleNightVision() {
    if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
      player.removePotionEffect(PotionEffectType.NIGHT_VISION);
      return;
    }

    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
  }

  public void checkVanishState() {
    if (!config.get().modules.isVanish()) return;

    StaffDataModel staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());

    if (staffDataModel.isVanished()) {
      enableVanish(staffMode);
    } else {
      disableVanish(true);
    }

    vanish = staffDataModel.isVanished();
    enableVanish(true);
  }

  public void addExtensions() {
    extensionManager.getExtensionsStream().forEach(extension -> {
      try {
        StaffPlayerExtension instance = extension.getConstructor(StaffPlayerWrapper.class).newInstance(this);
        injector.injectMembers(instance);

        extensionMap.put(extension, instance);
      } catch (Exception e) {
        logger.severe("An error occurred while adding extension %s for %s: %s", extension.getName(), player.getName(), e.getMessage());
      }
    });
  }

  public void loadPotionEffects() {
    if (config.get().staffMode.nightVision()) {
      if (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) toggleNightVision();
    }

    if (config.get().staffMode.customPotionEffects()) {
      config.get().staffMode.potionEffects().forEach(potionEffect -> {
        String[] split = potionEffect.split(":");

        if (split.length < 3) {
          logger.warning("Invalid potion effect format: %s", potionEffect);
          return;
        }

        PotionEffectType potionEffectType = Registry.EFFECT.get(Objects.requireNonNull(NamespacedKey.fromString(split[0].toLowerCase())));

        if (potionEffectType == null) {
          logger.warning("Invalid potion effect type: %s", split[0]);
          return;
        }

        int duration = Integer.parseInt(split[1]);

        int amplifier = Integer.parseInt(split[2]);

        player.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier, true, false));
      });
    }
  }

  public void unloadPotionEffects() {
    if (config.get().staffMode.nightVision()) {
      if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) toggleNightVision();
    }

    if (config.get().staffMode.customPotionEffects()) {
      config.get().staffMode.potionEffects().forEach(potionEffect -> {
        String[] split = potionEffect.split(":");

        if (split.length < 3) {
          logger.warning("Invalid potion effect format: %s", potionEffect);
          return;
        }

        PotionEffectType potionEffectType = Registry.EFFECT.get(Objects.requireNonNull(NamespacedKey.fromString(split[0].toLowerCase())));

        if (potionEffectType == null) {
          logger.warning("Invalid potion effect type: %s", split[0]);
          return;
        }

        player.removePotionEffect(potionEffectType);
      });
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends StaffPlayerExtension> Optional<T> getExtension(Class<T> extension) {
    StaffPlayerExtension staffPlayerExtension = extensionMap.get(extension);

    if (staffPlayerExtension == null) return Optional.empty();

    return Optional.of((T) staffPlayerExtension);
  }

  public static class Builder {
    private final StaffPaperPlayerWrapper playerWrapper;

    private Builder(StaffPaperPlayerWrapper playerWrapper) {
      this.playerWrapper = playerWrapper;
    }

    public static StaffPaperPlayerWrapper.Builder create(Injector injector) {
      return new StaffPaperPlayerWrapper.Builder(injector.getInstance(StaffPaperPlayerWrapper.class));
    }

    public StaffPaperPlayerWrapper.Builder setPlayer(Player player) {
      playerWrapper.player = player;
      return this;
    }

    public StaffPaperPlayerWrapper build() {
      if (playerWrapper.player == null) {
        throw new IllegalStateException("Player cannot be null");
      }

      playerWrapper.checkStaffModeState();
      playerWrapper.checkVanishState();
      playerWrapper.addExtensions();

      return playerWrapper;
    }
  }
}
