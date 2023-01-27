package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.gui.GuiUtils;
import es.angelillo15.mast.api.gui.TargetGUI;
import es.angelillo15.mast.api.managers.PunishmentsGUIManager;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.config.Messages;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.pagination.PaginationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PunishmentsGUI extends TargetGUI {
    public PunishmentsGUI(Player player, Player target) {
        super(player, target, "punishments", "Punishments", 6);
    }
    private final PaginationManager pagination = new PaginationManager(this);
    private int page = 1;


    public PunishmentsGUI(Player player) {
        super(player, "punishments", "Punishments", 6);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        PunishmentsGUIManager.getPage(page).getItems().forEach((slot, item) -> {
            addItem(slot, item);
        });

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
        addItem(GuiUtils.getPlayerHead(target));
        return false;
    }

}
