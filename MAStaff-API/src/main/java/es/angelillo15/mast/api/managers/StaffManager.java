package es.angelillo15.mast.api.managers;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.utils.MAStaffInject;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@Singleton
public class StaffManager {
  @Inject
  private MAStaffInject instance;
  @Inject
  private ILogger logger;
  @Getter
  private Cache<String, IStaffPlayer> staffPlayers = Caffeine.newBuilder().build();
  /**
   * @param staffPlayer The staff player to add
   */
  public void addStaffPlayer(IStaffPlayer staffPlayer) {
    staffPlayers.put(staffPlayer.getPlayer().getName(), staffPlayer);
  }
  /**
   * @param staffPlayer The staff player to remove
   */
  public void removeStaffPlayer(IStaffPlayer staffPlayer) {
    removeStaffPlayer(staffPlayer.getPlayer().getName());
  }
  /**
   * @param name The name of the staff player to remove
   */
  public void removeStaffPlayer(String name) {
    staffPlayers.invalidate(name);
  }
  /**
   * @param name The name of the staff player to get
   * @return The staff player
   */
  public IStaffPlayer getStaffPlayer(String name) {
    if (staffPlayers.getIfPresent(name) == null)
      return instance.createStaffPlayer(Bukkit.getPlayer(name));
    return staffPlayers.getIfPresent(name);
  }
  /**
   * @param player The name of the staff player to get
   * @return The staff player
   */
  public IStaffPlayer getStaffPlayer(Player player) {
    if (staffPlayers.getIfPresent(player.getName()) == null)
      return instance.createStaffPlayer(player);
    return staffPlayers.getIfPresent(player.getName());
  }
  /**
   * @param player The player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  public boolean isStaffPlayer(Player player) {
    return staffPlayers.getIfPresent(player.getName()) != null;
  }
  /**
   * @param name The player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  public boolean isStaffPlayer(String name) {
    return staffPlayers.getIfPresent(name) != null;
  }
  /**
   * @param staffPlayer The staff player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  public boolean isStaffPlayer(IStaffPlayer staffPlayer) {
    return staffPlayers.getIfPresent(staffPlayer.getPlayer().getName()) != null;
  }
}
