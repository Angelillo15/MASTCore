package es.angelillo15.mast.api.item;

import org.bukkit.Material;

public abstract class ExecutableItem {
    public abstract int slot();

    public abstract void click();

    public abstract Material material();

    public abstract int sfid();

}
