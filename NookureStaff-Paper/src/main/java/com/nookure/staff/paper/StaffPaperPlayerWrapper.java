package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.*;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.event.server.BroadcastMessageExcept;
import com.nookure.staff.api.event.staff.StaffModeDisabledEvent;
import com.nookure.staff.api.event.staff.StaffModeEnabledEvent;
import com.nookure.staff.api.extension.StaffPlayerExtension;
import com.nookure.staff.api.extension.StaffPlayerExtensionManager;
import com.nookure.staff.api.extension.VanishExtension;
import com.nookure.staff.api.item.StaffItem;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.model.StaffDataModel;
import com.nookure.staff.api.state.PlayerState;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.paper.data.StaffModeData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
  private final Map<Class<? extends StaffPlayerExtension>, StaffPlayerExtension> extensionMap = new HashMap<>();
  private final Map<Integer, StaffItem> items = new HashMap<>();

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
  private StaffDataModel staffDataModel;
  private boolean staffMode = false;
  private boolean staffChatAsDefault = false;
  private StaffModeData staffModeData;
  private VanishExtension vanishExtension;

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

    if (isInVanish()) disableVanish(false);
    else enableVanish(false);

    writeVanishState(isInVanish());
  }

  @Override
  public void enableVanish(boolean silent) {
    logger.debug("Enabling vanish for %s with previous state %s", player.getName(), isInVanish());
    if (isInVanish()) return;

    if (vanishExtension != null) {
      vanishExtension.enableVanish(silent);
    }
  }

  @Override
  public void disableVanish(boolean silent) {
    if (!isInVanish()) return;

    logger.debug("Disabling vanish for %s", player.getName());
    vanishExtension.disableVanish(silent);
  }

  @Override
  public boolean isInVanish() {
    if (vanishExtension == null) {
      return false;
    }

    return vanishExtension.isVanished();
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
      enableVanish(silentJoin);
      writeVanishState(true);
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
      disableVanish(false);
      writeVanishState(false);
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
    if (player.getGameMode() != GameMode.CREATIVE) {
      player.setAllowFlight(false);
    }

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

    if (vanishExtension != null) {
      vanishExtension.setVanished(state);
    }
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
    player.getInventory().setArmorContents(new ItemStack[0]);
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
      scheduler.async(() -> enableStaffMode(true));
    }

    staffChatAsDefault = staffDataModel.isStaffChatEnabled();
  }

  @Override
  public void toggleNightVision() {
    if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
      player.removePotionEffect(PotionEffectType.NIGHT_VISION);
      return;
    }

    scheduler.sync(() -> player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false)));
  }

  public void checkVanishState() {
    if (!config.get().modules.isVanish()) return;
    if (!vanishExtension.restoreFromDatabase()) return;
    StaffDataModel staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());

    if (staffDataModel.isVanished()) {
      enableVanish(staffMode);
    } else {
      disableVanish(true);
    }

    vanishExtension.setVanished(staffDataModel.isVanished());
  }

  public void addExtensions() {
    extensionManager.getExtensionsStream().forEach(extension -> {
      try {
        StaffPlayerExtension instance = extension.extension().getConstructor(StaffPlayerWrapper.class).newInstance(this);
        injector.injectMembers(instance);

        extensionMap.put(extension.extension(), instance);

        if (extension.base() != extension.extension()) {
          extensionMap.put(extension.base(), instance);
        }
      } catch (Exception e) {
        logger.severe("An error occurred while adding extension %s for %s: %s", extension.base().getName(), player.getName(), e.getMessage());
      }
    });

    vanishExtension = getExtension(VanishExtension.class).orElse(null);
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

  public void addActionBar() {
    if (!staffMode) return;
    if (!config.get().staffMode.actionBar()) return;
    if (!hasPermission(Permissions.ACTION_BAR_PERMISSION)) return;

    String vanished = isInVanish() ? messages.get().placeholder.placeholderTrue() : messages.get().placeholder.placeholderFalse();
    String staffChat = staffChatAsDefault ? messages.get().placeholder.placeholderTrue() : messages.get().placeholder.placeholderFalse();
    double tpsCount = Bukkit.getTPS()[0];
    String tps = String.valueOf(tpsCount);
    tps = tps.substring(0, Math.min(tps.length(), 5));

    if (tpsCount > 18) {
      tps = "<green>" + tps;
    } else if (tpsCount > 15) {
      tps = "<yellow>" + tps;
    } else {
      tps = "<red>" + tps;
    }

    sendActionbar(MiniMessage.miniMessage().deserialize(
            messages.get().staffMode.actionBar()
                .replace("{vanished}", vanished)
                .replace("{staffChat}", staffChat)
                .replace("{tps}", tps)
        )
    );
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
    private final HashMap<Class<? extends PlayerState>, PlayerState> states = new HashMap<>();

    private Builder(StaffPaperPlayerWrapper playerWrapper) {
      this.playerWrapper = playerWrapper;
    }

    public static StaffPaperPlayerWrapper.Builder create(Injector injector) {
      return new StaffPaperPlayerWrapper.Builder(injector.getInstance(StaffPaperPlayerWrapper.class));
    }

    public StaffPaperPlayerWrapper.Builder setPlayer(Player player) {
      if (player == null) {
        return this;
      }

      playerWrapper.player = player;
      return this;
    }

    public StaffPaperPlayerWrapper.Builder addState(Class<? extends PlayerState> state) {
      PlayerState playerState;

      try {
        playerState = state.getConstructor(PlayerWrapper.class).newInstance(playerWrapper);
      } catch (Exception e) {
        throw new IllegalStateException("An error occurred while adding the state", e);
      }

      states.put(state, playerState);
      return this;
    }

    public StaffPaperPlayerWrapper.Builder setModel(PlayerModel model) {
      playerWrapper.playerModel = model;
      return this;
    }

    public StaffPaperPlayerWrapper build() {
      if (playerWrapper.player == null) {
        throw new IllegalStateException("Player cannot be null");
      }

      if (playerWrapper.playerModel == null) {
        playerWrapper.playerModel = playerWrapper.getPlayerModel();
      }

      states.forEach((k, v) -> playerWrapper.getState().setState(v));

      playerWrapper.scheduler.sync(playerWrapper::addExtensions);

      playerWrapper.checkStaffModeState();
      playerWrapper.checkVanishState();

      return playerWrapper;
    }
  }
}
