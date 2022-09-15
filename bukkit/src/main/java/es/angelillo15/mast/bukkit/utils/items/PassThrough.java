package es.angelillo15.mast.bukkit.utils.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PassThrough {

    public static boolean passThrough(Player player, Location clickedLocation) {
        Vector direction = player.getLocation().getDirection().normalize();
        do {
            clickedLocation.add(direction);
        } while (clickedLocation.getBlock().getType().equals(Material.AIR));
        player.teleport(clickedLocation);
        return true;
    }
    
}
