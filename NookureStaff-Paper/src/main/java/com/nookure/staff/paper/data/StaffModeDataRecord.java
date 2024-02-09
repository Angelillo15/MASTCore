package com.nookure.staff.paper.data;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class StaffModeDataRecord implements Serializable {
  private ItemStack[] playerInventory;
  private ItemStack[] playerInventoryArmor;
  private ItemStack[] staffVault;
  private boolean staffMode;

  public StaffModeDataRecord(
      ItemStack[] playerInventory,
      ItemStack[] playerInventoryArmor,
      ItemStack[] staffVault,
      boolean staffMode
  ) {
    this.playerInventory = playerInventory;
    this.playerInventoryArmor = playerInventoryArmor;
    this.staffVault = staffVault;
    this.staffMode = staffMode;
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
}
