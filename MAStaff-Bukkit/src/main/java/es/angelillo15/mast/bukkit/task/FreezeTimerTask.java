package es.angelillo15.mast.bukkit.task;

import com.google.inject.Inject;
import com.nookure.mast.api.manager.FreezeManager;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.nms.VersionSupport;
import es.angelillo15.mast.bukkit.MAStaff;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FreezeTimerTask implements Runnable {
  @Inject
  private FreezeManager freezeManager;
  @Inject
  private VersionSupport versionSupport;

  @Override
  public void run() {
    freezeManager.getFreezeVectors().forEach(player -> {
      if (player.getTimeLeft() != -1 && player.getTimeLeft() < System.currentTimeMillis()) {
        if (player instanceof Player bp) {
          freezeManager.unfreezePlayer(bp);
        }

        Bukkit.getScheduler().runTaskLater(MAStaff.getPlugin(), () -> {
          if (player.getTimeLeft() == -2) return;

          player.getStaffPlayer().executeFreezedPunishments(player.getTarget().getName());
          player.setTimeLeft(-2);
        }, 1L);
      }

      if (player.getTimeLeft() != -1 && player.getTarget() instanceof Player bp) {
        if (MAStaff.isFree()) return;

        Component cmp = TextUtils.toComponent(
            "<red>" + TextUtils.formatUptime(player.getTimeLeft() - System.currentTimeMillis())
        );

        versionSupport.sendActionBar(bp, cmp);
      }
    });
  }
}
