package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.gui.GuiUtils;
import es.angelillo15.mast.api.gui.TargetGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class PunishmentsGUI extends TargetGUI {
    public PunishmentsGUI(Player player, Player target) {
        super(player, target, "punishments", "Punishments", 6);
    }

    public PunishmentsGUI(Player player) {
        super(player, "punishments", "Punishments", 6);
    }

    @Override
    public boolean onClick(InventoryClickEvent event) {
        addItem(GuiUtils.getPlayerHead(target));
        return false;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        super.onOpen(event);
    }
}
