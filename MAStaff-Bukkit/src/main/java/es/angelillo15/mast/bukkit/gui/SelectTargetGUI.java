package es.angelillo15.mast.bukkit.gui;

import mc.obliviate.inventory.Gui;
import org.bukkit.entity.Player;

public class SelectTargetGUI extends Gui {
    public SelectTargetGUI(Player player, String id, String title, int rows) {
        super(player, id, title, rows);
    }
}
