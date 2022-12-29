package es.angelillo15.mast.api.items;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IExecutableLocationItem {
    void click(Player player, Location location);
}
