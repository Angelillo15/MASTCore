package com.nookure.mast.api.manager;

import com.google.inject.Singleton;
import es.angelillo15.mast.api.IStaffPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@Singleton
public class FreezeManager {
  public HashMap<String, FreezeVector> frozenPlayers = new HashMap<>();

  /**
   * Freeze the player
   * @param staff IStaffPlayer who freeze the player
   * @param target Player to freeze
   */
  public void freezePlayer(IStaffPlayer staff, Player target) {
    freezePlayer(staff, target, -1);
  }

  /**
   * Freeze the player
   * @param staff IStaffPlayer who freeze the player
   * @param target Player to freeze
   * @param time Time to freeze the player (in ms)
   */
  public void freezePlayer(IStaffPlayer staff, Player target, long time) {
    frozenPlayers.put(target.getName(), new FreezeVector(staff, target, System.currentTimeMillis() + time));
  }

  /**
   * Unfreeze the player
   * @param target Player
   */
  public void unfreezePlayer(Player target) {
    frozenPlayers.remove(target.getName());
  }

  /**
   * Unfreeze the player
   * @param target Player
   */
  public void unfreezePlayer(String target) {
    frozenPlayers.remove(target);
  }

  /**
   * Check if the player is frozen
   * @param target Player
   * @return boolean
   */
  public boolean isFrozen(Player target) {
    return frozenPlayers.containsKey(target.getName());
  }

  /**
   * Check if the player is frozen
   * @param target Player
   * @return boolean
   */
  public boolean isFrozen(String target) {
    return frozenPlayers.containsKey(target);
  }

  /**
   * Gets the frozen players list
   * @return ArrayList of OfflinePlayer
   */
  public ArrayList<OfflinePlayer> getFrozenPlayers() {
    ArrayList<OfflinePlayer> frozen = new ArrayList<>();
    frozenPlayers.forEach((name, vector) -> frozen.add(vector.getTarget()));
    return frozen;
  }

  public FreezeVector getFreezeVector(Player target) {
    return frozenPlayers.get(target.getName());
  }
}
