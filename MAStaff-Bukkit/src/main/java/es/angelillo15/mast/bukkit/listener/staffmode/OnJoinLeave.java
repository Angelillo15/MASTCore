package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.staff.StaffLeaveEvent;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.database.sql.CommonQueries;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.api.utils.VersionUtils;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.StaffPlayer;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnJoinLeave implements Listener {
  @Inject
  private StaffManager staffManager;
  @Inject
  private EventManager eventManager;

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if (!staffManager.isStaffPlayer(player)) return;

    MAStaff.getPlugin()
        .getPLogger()
        .debug("Status: " + CommonQueries.isInStaffMode(player.getUniqueId()));

    IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

    if (CommonQueries.isInStaffMode(player.getUniqueId())) {
      MAStaff.getPlugin()
          .getPLogger()
          .debug("Player " + player.getName() + " previous state: " + staffPlayer.wasInStaffMode());

      staffPlayer.toggleStaffMode(!staffPlayer.wasInStaffMode());
    }

    if (staffPlayer.isStaffMode()) {
      StaffUtils.asyncStaffChatMessage(
          Messages.GET_STAFF_VANISH_JOIN_MESSAGE().replace("{player}", player.getName()));
      event.setJoinMessage("");
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    if (VersionUtils.getBukkitVersion() > 8) player.setGlowing(false);

    if (!staffManager.isStaffPlayer(player)) {
      return;
    }

    eventManager.fireEvent(new StaffLeaveEvent(player.getName()));

    StaffPlayer staffPlayer = (StaffPlayer) staffManager.getStaffPlayer(player);

    if (staffPlayer == null) {
      return;
    }

    if (staffPlayer.isStaffMode()) {
      StaffUtils.asyncStaffChatMessage(
          Messages.GET_STAFF_VANISH_LEAVE_MESSAGE().replace("{player}", player.getName()));
      event.setQuitMessage("");
    }

    if (Config.disableStaffModeOnExit()) {
      staffPlayer.setQuit(true);
      staffPlayer.toggleStaffMode(true);
    }

    if (staffPlayer.existsData() && staffPlayer.isStaffMode()) {
      staffPlayer.clearInventory();
      staffPlayer.restoreInventory();
      staffPlayer.restoreHealthAndFood();
      player.setAllowFlight(false);
      staffPlayer.changeGamemode(GameMode.SURVIVAL);
      staffPlayer.removeEffects();
    }

    staffManager.removeStaffPlayer(staffPlayer);
  }
}
