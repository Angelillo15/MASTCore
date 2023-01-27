package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.gui.GuiUtils;
import es.angelillo15.mast.api.gui.TargetGUI;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.config.Messages;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.pagination.PaginationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectTargetGUI extends Gui {
    private TargetGUI targetGui;
    private final PaginationManager pagination = new PaginationManager(this);

    public SelectTargetGUI(Player player, TargetGUI targetGUI) {
        super(player, "select-target", "Select a target", 6);
        this.targetGui = targetGUI;
    }



    @Override
    public void onOpen(InventoryOpenEvent event) {

        Bukkit.getOnlinePlayers().forEach(people -> {
            ItemStack item = GuiUtils.getPlayerHead(people, Messages.GET_STAFFLIST_LORE());
            pagination.addItem(new Icon(item));
        });


        this.pagination.registerPageSlotsBetween(10, 16);
        this.pagination.registerPageSlotsBetween(19, 25);
        this.pagination.registerPageSlotsBetween(28, 34);
        this.pagination.registerPageSlotsBetween(37, 43);

        this.pagination.update();

        ItemStack previousPage = XMaterial.ARROW.parseItem();
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        previousPageMeta.setDisplayName(Messages.GET_STAFFLIST_PREVIUS());
        previousPage.setItemMeta(previousPageMeta);

        ItemStack nextPage = XMaterial.ARROW.parseItem();
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        nextPageMeta.setDisplayName(Messages.GET_STAFFLIST_NEXT());
        nextPage.setItemMeta(nextPageMeta);

        addItem(45, previousPage);
        addItem(53, nextPage);
    }

    @Override
    public boolean onClick(InventoryClickEvent event) {
        super.onClick(event);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return false;
        if (clickedItem.getItemMeta() == null) return false;

        ItemMeta clickedItemMeta = clickedItem.getItemMeta();

        if (clickedItemMeta.getDisplayName() == null) return false;

        Player target = MAStaff.getPlugin().getServer().getPlayer(clickedItemMeta.getDisplayName());

        if (target == null) return false;

        targetGui.setTarget(target);
        player.closeInventory();

        targetGui.open();
        return false;
    }
    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
    }
}
