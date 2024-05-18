package com.nookure.staff.paper.data;

import com.nookure.staff.api.util.LocationWrapper;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public class StaffModeDataRecord implements Serializable {
  @Serial
  private static final long serialVersionUID = 2222L;
  private ItemStack[] playerInventory;
  private ItemStack[] playerInventoryArmor;
  private ItemStack[] staffVault;
  private boolean staffMode;
  private LocationWrapper enabledLocation;

  public StaffModeDataRecord(
      ItemStack[] playerInventory,
      ItemStack[] playerInventoryArmor,
      ItemStack[] staffVault,
      boolean staffMode,
      Location enabledLocation
  ) {
    this.playerInventory = playerInventory;
    this.playerInventoryArmor = playerInventoryArmor;
    this.staffVault = staffVault;
    this.staffMode = staffMode;
    this.enabledLocation = new LocationWrapper(enabledLocation);
  }

  public ItemStack[] playerInventory() {
    return playerInventory;
  }

  public void playerInventory(ItemStack[] playerInventory) {
    this.playerInventory = playerInventory;
  }

  public ItemStack[] playerInventoryArmor() {
    return playerInventoryArmor;
  }

  public void playerInventoryArmor(ItemStack[] playerInventoryArmor) {
    this.playerInventoryArmor = playerInventoryArmor;
  }

  public ItemStack[] staffVault() {
    return staffVault;
  }

  public void staffVault(ItemStack[] staffVault) {
    this.staffVault = staffVault;
  }

  public boolean staffMode() {
    return staffMode;
  }

  public void staffMode(boolean staffMode) {
    this.staffMode = staffMode;
  }

  public Location enabledLocation() {
    return LocationWrapper.toLocation(enabledLocation);
  }

  public void enabledLocation(Location enabledLocation) {
    this.enabledLocation = new LocationWrapper(enabledLocation);
  }

  @Override
  public String toString() {
    return "StaffModeDataRecord{" +
        "playerInventory=" + Arrays.toString(playerInventory) +
        ", playerInventoryArmor=" + Arrays.toString(playerInventoryArmor) +
        ", staffVault=" + Arrays.toString(staffVault) +
        ", staffMode=" + staffMode +
        '}';
  }
}
