package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnAttack implements Listener {
    @Inject
    private StaffManager staffManager;
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();

        if (!staffManager.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
