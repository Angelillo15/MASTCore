package es.angelillo15.mast.bukkit.listener.staffmode.achivement;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnAchievement implements Listener {
    @Inject
    private StaffManager staffManager;

    @EventHandler
    public void onAchievement(PlayerAdvancementCriterionGrantEvent event) {
        Player player = event.getPlayer();

        if (!staffManager.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
