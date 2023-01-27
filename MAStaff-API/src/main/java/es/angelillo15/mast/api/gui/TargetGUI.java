package es.angelillo15.mast.api.gui;

import lombok.Getter;
import lombok.Setter;
import mc.obliviate.inventory.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class TargetGUI extends Gui {
    @Getter
    @Setter
    private Player target;

    public TargetGUI(Player player, String id, String title, int rows) {
        super(player, id, title, rows);
    }

    public TargetGUI(Player player, String id, String title, InventoryType inventoryType) {
        super(player, id, title, inventoryType);
    }

    public TargetGUI(Player player, Player target, String id, String title, InventoryType inventoryType){
        super(player, id, title, inventoryType);
        this.target = target;
    }

    public TargetGUI(Player player, Player target, String id, String title, int rows) {
        super(player, id, title, rows);
        this.target = target;
    }
}
