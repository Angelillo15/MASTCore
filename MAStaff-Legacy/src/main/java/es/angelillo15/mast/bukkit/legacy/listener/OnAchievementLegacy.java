package es.angelillo15.mast.bukkit.legacy.listener;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.plugin.Plugin;

public class OnAchievementLegacy implements Listener {
    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent event) {
        MAStaffInstance<Plugin> instance = MAStaffInstance.getInstance();

        instance.getPLogger().info("Achievement: " + event.getAchievement().name());

        Player player = event.getPlayer();

        if (!StaffPlayersManagers.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if (staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
