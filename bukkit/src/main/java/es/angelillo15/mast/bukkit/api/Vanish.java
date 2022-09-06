package es.angelillo15.mast.bukkit.api;

import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Vanish {
    private Player player;
    public Vanish(Player player){
        this.player = player;
    }

    public void enableVanish(){
        Bukkit.getServer().getPluginManager().callEvent(new PlayerVanishEnableEvent(player));
    }
}
