package es.angelillo15.mast.api.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class ExecutableItem {
    public abstract int getSlot();

    public abstract void click(Player player);

    public abstract Material getMaterial();

    public abstract int getSfid();

}
