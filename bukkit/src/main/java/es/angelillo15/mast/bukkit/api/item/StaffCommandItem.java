package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.entity.Player;

public interface StaffCommandItem {
    public void click(Player player, String command);
    public String getCommand();
}
