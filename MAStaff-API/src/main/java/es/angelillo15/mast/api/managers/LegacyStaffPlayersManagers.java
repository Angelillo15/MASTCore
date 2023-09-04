package es.angelillo15.mast.api.managers;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Deprecated
public class LegacyStaffPlayersManagers {
  @Inject private static StaffManager staffManager;

  @Deprecated
  public static IStaffPlayer getStaffPlayer(@NonNull String name) {
    return staffManager.getStaffPlayer(name);
  }

  @Deprecated
  public static IStaffPlayer getStaffPlayer(@NonNull Player player) {
    return staffManager.getStaffPlayer(player);
  }

  /**
   * @param player The player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  @Deprecated
  public static boolean isStaffPlayer(@NonNull Player player) {
    return staffManager.isStaffPlayer(player);
  }

  /**
   * @param name The player to check if is in the map
   * @return true if the player is in the map or false if not
   */
  @Deprecated
  public static boolean isStaffPlayer(@NonNull String name) {
    return staffManager.isStaffPlayer(name);
  }
}
