package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.event.bukkit.freeze.FreezeMessageEvent;
import es.angelillo15.mast.api.managers.freeze.FreezeManager;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import org.bukkit.Bukkit;

public class FreezeUtils {
    public static void setupMessageSender(){
        Scheduler.executeTimerAsync(() -> {
            FreezeManager.getFrozenPlayers().forEach(player -> {
                if (player.isOnline()) {
                    Bukkit.getPluginManager().callEvent(new FreezeMessageEvent(player.getPlayer()));
                }
            });
        }, 0, 20*5);
    }
}
