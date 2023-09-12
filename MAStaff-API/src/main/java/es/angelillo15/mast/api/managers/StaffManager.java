package es.angelillo15.mast.api.managers;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.utils.MAStaffInject;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class StaffManager {
  @Inject
  private MAStaffInject instance;
  @Getter
  private final Map<String, IStaffPlayer> staffPlayers = new ConcurrentHashMap<>();

  /**
   * @param staffPlayer The staff player to add
   */
  public void addStaffPlayer(@NotNull IStaffPlayer staffPlayer) {
    staffPlayers.put(staffPlayer.getPlayer().getName(), staffPlayer);
  }

  /**
   * @param staffPlayer The staff player to remove
   */
  public void removeStaffPlayer(@NotNull IStaffPlayer staffPlayer) {
    removeStaffPlayer(staffPlayer.getPlayer().getName());
  }

  /**
   * @param name The name of the staff player to remove
   */
  public void removeStaffPlayer(@NotNull String name) {
    staffPlayers.remove(name);
  }

  /**
   * @param name The name of the staff player to get
   * @return The staff player
   */
  @Nullable
  public IStaffPlayer getStaffPlayer(@NotNull String name) {
    return getStaffPlayer(Objects.requireNonNull(Bukkit.getPlayer(name)));
  }

  /**
   * @param player The name of the staff player to get
   * @return The staff player
   */
  @Nullable
  public IStaffPlayer getStaffPlayer(@NotNull Player player) {
    if (!staffPlayers.containsKey(player.getName()))
      return instance.createStaffPlayer(player);
    return staffPlayers.get(player.getName());
  }

  /**
   * @param player The player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  public boolean isStaffPlayer(@NotNull Player player) {
    return isStaffPlayer(player.getName());
  }

  /**
   * @param name The player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  public boolean isStaffPlayer(@NotNull String name) {
    return staffPlayers.containsKey(name);
  }

  /**
   * @param staffPlayer The staff player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  public boolean isStaffPlayer(@NotNull IStaffPlayer staffPlayer) {
    return staffPlayers.containsValue(staffPlayer);
  }
}
