package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.database.AbstractPluginConnection;
import com.nookure.staff.api.item.StaffItem;
import com.nookure.staff.api.manager.StaffItemsManager;
import com.nookure.staff.api.model.StaffDataModel;
import com.nookure.staff.paper.data.StaffModeData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StaffPaperPlayerWrapper extends PaperPlayerWrapper implements StaffPlayerWrapper {
  @Inject
  private NookureStaff plugin;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  @Inject
  private StaffItemsManager itemsManager;
  @Inject
  private AbstractPluginConnection connection;
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
    enablePlayerPerks();
    saveInventory();
    setItems();
    sendMiniMessage(messages.get().staffMode.toggledOn());
    staffMode = true;
  }

  private void disableStaffMode() {
    disablePlayerPerks();
    restoreInventory();
    sendMiniMessage(messages.get().staffMode.toggledOff());
    staffMode = false;
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
    staffModeData.record().staffMode(true);

    staffModeData.write();

    clearInventory();
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
    staffModeData.record().staffMode(false);

    staffModeData.write();
  }

  private void checkState() {
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

      playerWrapper.checkState();
      return playerWrapper;
    }
  }
}
