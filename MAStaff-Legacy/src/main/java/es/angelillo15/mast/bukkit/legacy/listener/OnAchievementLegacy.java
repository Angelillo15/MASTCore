package es.angelillo15.mast.bukkit.legacy.listener;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.LegacyStaffPlayersManagers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class OnAchievementLegacy implements Listener {
    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent event) {
        Player player = event.getPlayer();

        if (!LegacyStaffPlayersManagers.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = LegacyStaffPlayersManagers.getStaffPlayer(player);

        if (staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
