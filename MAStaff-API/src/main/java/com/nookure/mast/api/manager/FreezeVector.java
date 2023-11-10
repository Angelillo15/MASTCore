package com.nookure.mast.api.manager;

import es.angelillo15.mast.api.IStaffPlayer;
import org.bukkit.OfflinePlayer;


public class FreezeVector {
  private final IStaffPlayer staffPlayer;
  private final OfflinePlayer target;
  private long timeLeft;
  private boolean hasTalked = false;

  public FreezeVector(IStaffPlayer staffPlayer, OfflinePlayer target, long timeLeft) {
    this.staffPlayer = staffPlayer;
    this.target = target;
    this.timeLeft = timeLeft;
  }

  public IStaffPlayer getStaffPlayer() {
    return staffPlayer;
  }

  public OfflinePlayer getTarget() {
    return target;
  }

  /**
   * Get the time left to unfreeze the player
   * -1 disabled
   * -2 expired
   * @return long time left
   */
  public long getTimeLeft() {
    return timeLeft;
  }

  public void setTimeLeft(int timeLeft) {
    this.timeLeft = timeLeft;
  }

  public boolean hasTalked() {
    return hasTalked;
  }

  public void setHasTalked(boolean hasTalked) {
    this.hasTalked = hasTalked;
  }
}
