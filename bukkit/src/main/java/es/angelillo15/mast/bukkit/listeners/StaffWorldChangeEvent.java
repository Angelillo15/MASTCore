package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class StaffWorldChangeEvent implements Listener {
    @EventHandler
    public void onStaffWorldChange(PlayerChangedWorldEvent event){
        if(MASTBukkitManager.getInstance().containsStaffPlayer(event.getPlayer().getUniqueId())){
            event.getPlayer().setGameMode(GameMode.CREATIVE);
        }
    }
}
