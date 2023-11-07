package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.event.bukkit.freeze.FreezeMessageEvent;
import es.angelillo15.mast.api.managers.freeze.FreezeManager;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import org.bukkit.Bukkit;

public class FreezeUtils {
  private static final FreezeManager freezeManager = MAStaff.getPlugin().getInjector().getInstance(FreezeManager.class);
  public static void setupMessageSender() {
    Scheduler.executeTimerAsync(
        () -> {
          freezeManager.getFrozenPlayers()
              .forEach(
                  player -> {
                    if (player.isOnline()) {
                      Bukkit.getPluginManager()
                          .callEvent(new FreezeMessageEvent(player.getPlayer()));
                    }
                  });
        },
        0,
        20 * 5);
  }
}
