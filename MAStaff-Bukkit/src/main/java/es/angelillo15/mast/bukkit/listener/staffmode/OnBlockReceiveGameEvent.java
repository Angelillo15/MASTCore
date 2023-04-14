package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockReceiveGameEvent;

public class OnBlockReceiveGameEvent implements Listener {
    @EventHandler
    public void onBlockReceiveGameEvent(BlockReceiveGameEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if (!StaffPlayersManagers.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.isVanished()) event.setCancelled(true);
    }
}
