package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface StaffItem {
    int getSlot();
    Player getPlayer();

    String getName();

    Material getMaterial();

    ArrayList<String> getLore();

    void click();

}
