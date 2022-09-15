package es.angelillo15.mast.bukkit.api.item.types;

import org.bukkit.entity.Player;

public interface EntityInteractItem {
    public Player getTarget();
    public void setTarget(Player target);
    public void click(Player player, Player target);
}
