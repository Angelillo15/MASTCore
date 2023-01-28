package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.gui.CommandItem;
import es.angelillo15.mast.api.gui.GuiUtils;
import es.angelillo15.mast.api.gui.TargetGUI;
import es.angelillo15.mast.api.managers.PunishmentsGUIManager;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.config.Messages;
import mc.obliviate.inventory.pagination.PaginationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PunishmentsGUI extends TargetGUI {
    public PunishmentsGUI(Player player, Player target) {
        super(player, target, "punishments", Messages.GET_GUI_TITLE(), 6);
    }
    private int page = 1;
    public PunishmentsGUI(Player player, Player target, int page){
        super(player, target, "punishments", Messages.GET_GUI_TITLE()
                .replace("{page_title}", PunishmentsGUIManager.getPage(page).getTitle()),
                6);
        this.page = page;
    }

    public PunishmentsGUI(Player player){
        super(player, "punishments", Messages.GET_GUI_TITLE()
                .replace("{page_title}", PunishmentsGUIManager.getPage(1).getTitle()),
                6);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        addItems();
    }

    public void addItems() {
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
        super.onClick(event);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return false;
        if (clickedItem.getItemMeta() == null) return false;

        ItemMeta clickedItemMeta = clickedItem.getItemMeta();

        if (clickedItemMeta.getDisplayName() == null) return false;

        String clickedItemDisplayName = clickedItemMeta.getDisplayName();

        if(clickedItemDisplayName.equals(Messages.GET_STAFFLIST_NEXT())) {
            if (page < PunishmentsGUIManager.getPunishmentsGUIS().size()) {
                page++;
                addItems();
            }

            player.closeInventory();
            new PunishmentsGUI(player, target, page).open();
            return false;
        }

        if(clickedItemDisplayName.equals(Messages.GET_STAFFLIST_PREVIUS())) {
            if (page > 1) {
                page--;

                addItems();
            }

            player.closeInventory();
            new PunishmentsGUI(player, target, page).open();
            return false;
        }


        CommandItem commandItem = PunishmentsGUIManager.getPage(page).getItem(event.getSlot());

        if (commandItem != null) {
            player.performCommand(commandItem.getCommand()
                    .replace("{target}", target.getName())
                    .replace("{sender}", player.getName())
            );
            return false;
        }

        return false;
    }

}
