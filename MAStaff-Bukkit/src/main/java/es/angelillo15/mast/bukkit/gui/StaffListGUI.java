package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.material.XMaterial;
import mc.obliviate.inventory.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class StaffListGUI extends Gui {
    public StaffListGUI(Player player) {
        super(player, "staff-list", "StaffList", 6);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        ItemStack item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial());

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setDisplayName(player.getDisplayName());

        meta.setOwningPlayer(player);

        item.setItemMeta(meta);
    }
}
