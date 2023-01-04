package es.angelillo15.mast.bukkit.listener;

import es.angelillo15.mast.api.managers.GlowManager;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GlowJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskAsynchronously(MAStaff.getPlugin(), () -> {
            GlowManager.getGlows().forEach((color, glow) -> {
                glow.display(event.getPlayer());
            });
        });
    }
}
