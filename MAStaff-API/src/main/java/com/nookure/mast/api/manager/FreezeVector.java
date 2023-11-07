package com.nookure.mast.api.manager;

import es.angelillo15.mast.api.IStaffPlayer;
import org.bukkit.OfflinePlayer;


public class FreezeVector {
  IStaffPlayer staffPlayer;
  OfflinePlayer target;
  long timeLeft;

  public FreezeVector(IStaffPlayer staffPlayer, OfflinePlayer target, long timeLeft) {
    this.staffPlayer = staffPlayer;
    this.target = target;
    this.timeLeft = timeLeft;
  }

  public IStaffPlayer getStaffPlayer() {
    return staffPlayer;
  }

  public void setStaffPlayer(IStaffPlayer staffPlayer) {
    this.staffPlayer = staffPlayer;
  }

  public OfflinePlayer getTarget() {
    return target;
  }

  public void setTarget(OfflinePlayer target) {
    this.target = target;
  }

  public long getTimeLeft() {
    return timeLeft;
  }

  public void setTimeLeft(int timeLeft) {
    this.timeLeft = timeLeft;
  }
}
