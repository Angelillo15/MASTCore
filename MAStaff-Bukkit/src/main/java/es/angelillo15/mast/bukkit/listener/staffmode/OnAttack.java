package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnAttack implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();

        if (!StaffPlayersManagers.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
