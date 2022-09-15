package es.angelillo15.mast.bukkit.api.item.types;

import org.bukkit.entity.Player;

public interface ExecutableInteractionItems {
    public void click(Player player, Player target);

    public String getCommand();
}
