package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class OnItemGet implements Listener {
    @Inject
    private StaffManager staffManager;

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onItemGet(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if (!staffManager.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

        if (staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
