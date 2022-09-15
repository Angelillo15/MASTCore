package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.entity.Player;

public interface EntityInteractItem {
    public Player getTarget();
    public void setTarget(Player target);
}
