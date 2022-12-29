package es.angelillo15.mast.api.items;

import org.bukkit.entity.Player;

public interface ICommandItem {
    public void execute(Player player, String command);
    public void execute(Player player);
}
