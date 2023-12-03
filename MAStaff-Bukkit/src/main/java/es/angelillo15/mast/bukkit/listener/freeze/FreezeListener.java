package es.angelillo15.mast.bukkit.listener.freeze;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.event.bukkit.freeze.FreezeMessageEvent;
import com.nookure.mast.api.manager.FreezeManager;
import com.nookure.mast.api.manager.FreezeVector;
import es.angelillo15.mast.bukkit.MAStaff;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {
  @Inject
  private FreezeManager freezeManager;

  @EventHandler
  public void onFreezeEvent(PlayerMoveEvent event) {
    if (freezeManager.isFrozen(event.getPlayer())) {
      if (event.getFrom().getX() != event.getTo().getX()
          || event.getFrom().getY() != event.getTo().getY()
          || event.getFrom().getZ() != event.getTo().getZ()) {
        Location loc = event.getFrom();

        PaperLib.teleportAsync(event.getPlayer(), loc.setDirection(event.getTo().getDirection()));
      }
    }
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (freezeManager.isFrozen(event.getPlayer())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void freezedPlayerExit(PlayerQuitEvent event) {
    if (!Config.Freeze.executeCommandOnExit()) {
      freezeManager.unfreezePlayer(event.getPlayer());
      return;
    }

    if (!freezeManager.isFrozen(event.getPlayer())) return;

    if (MAStaff.isFree()) return;

    Player player = event.getPlayer();
    FreezeVector vector = freezeManager.getFreezeVector(player);

    if (vector == null) return;

    IStaffPlayer staffPlayer = vector.getStaffPlayer();

    if (staffPlayer == null) return;

    if (!Config.Freeze.askToExecuteCommands()) {
      freezeManager.unfreezePlayer(player);

      staffPlayer.executeFreezedPunishments(player.getName());

      return;
    }

    TextUtils.sendMessage(
        staffPlayer.getPlayer(),
        Messages.CONFIRM_PUNISH_MESSAGE().replace("{player}", player.getName()));
  }
}
