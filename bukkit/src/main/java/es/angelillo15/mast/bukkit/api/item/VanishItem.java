package es.angelillo15.mast.bukkit.api.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishItem implements StaffItem{
    private int slot;
    private String name;
    private Material material;
    private ArrayList<String> lore;
    private Player player;

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ArrayList<String> getLore() {
        return lore;
    }

    @Override
    public void click() {

    }
}
