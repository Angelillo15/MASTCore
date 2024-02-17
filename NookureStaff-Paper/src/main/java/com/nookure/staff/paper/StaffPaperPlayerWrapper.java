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
import com.nookure.staff.api.item.StaffItem;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.model.StaffDataModel;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.paper.data.StaffModeData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
  private final Map<Integer, StaffItem> items = new HashMap<>();
  private StaffDataModel staffDataModel;
  private boolean staffMode = false;
  private boolean vanish = false;
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

    if (!silent) sendMiniMessage(messages.get().vanish.vanishEnabled());

    Bukkit.getOnlinePlayers().stream()
        .filter(p -> !p.hasPermission(Permissions.STAFF_VANISH_SEE))
        .forEach(p -> p.hidePlayer(javaPlugin, player));
  }

  @Override
  public void disableVanish(boolean silent) {
    if (vanish) return;

    logger.debug("Disabling vanish for %s", player.getName());

    if (!silent) sendMiniMessage(messages.get().vanish.vanishDisabled());

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
    saveInventory();
    if (silentJoin) saveLocation();
    setItems();
    sendMiniMessage(messages.get().staffMode.toggledOn());
    writeStaffModeState(true);
    enableVanish();

    logger.debug("Staff mode enabled for %s in %dms", player.getName(), System.currentTimeMillis() - time);
  }

  private void disableStaffMode() {
    long time = System.currentTimeMillis();
    disablePlayerPerks();
    restoreInventory();
    loadPreviousLocation();
    sendMiniMessage(messages.get().staffMode.toggledOff());
    writeStaffModeState(false);

    logger.debug("Staff mode disabled for %s in %dms", player.getName(), System.currentTimeMillis() - time);
  }

  public void enablePlayerPerks() {
    player.setAllowFlight(true);
    player.setFlying(true);
    player.setInvulnerable(true);
  }

  public void disablePlayerPerks() {
    player.setAllowFlight(false);
    player.setFlying(false);
    player.setInvulnerable(false);
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
    player.teleport(staffModeData.record().enabledLocation());
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

  private void checkStaffModeState() {
    if (staffModeData == null) {
      staffModeData = StaffModeData.read(plugin, this);
    }

    assert staffModeData != null;

    if (staffModeData.record().staffMode()) {
      enableStaffMode(true);
      return;
    }

    if (staffDataModel == null) {
      staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());
    }

    if (staffDataModel.isStaffMode()) {
      enableStaffMode(true);
    }
  }

  public void checkVanishState() {
    if (!config.get().modules.isVanish()) return;

    StaffDataModel staffDataModel = StaffDataModel.getFromUUID(connection.getStorm(), player.getUniqueId());

    if (staffDataModel.isVanished()) {
      enableVanish();
    } else {
      disableVanish();
    }

    vanish = staffDataModel.isVanished();
    enableVanish(true);
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

      return playerWrapper;
    }
  }
}
