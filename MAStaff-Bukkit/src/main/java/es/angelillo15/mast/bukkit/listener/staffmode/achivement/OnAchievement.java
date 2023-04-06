package es.angelillo15.mast.bukkit.listener.staffmode.achivement;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnAchievement implements Listener {
    @EventHandler
    public void onAchievement(PlayerAdvancementCriterionGrantEvent event) {
        Player player = event.getPlayer();

        if (!StaffPlayersManagers.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
