package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.gui.GuiUtils;
import es.angelillo15.mast.api.gui.TargetGUI;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.config.Messages;
import lombok.Getter;
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

import java.util.function.Consumer;

public class SelectTargetGUI extends Gui {
    enum CallbackType {
        GUI,
        RUNNABLE
    }

    private CallbackType callbackType;
    private Consumer<Player> callback;
    private TargetGUI targetGui;
    @Getter
    private Player targetPlayer;
    private final PaginationManager pagination = new PaginationManager(this);

    public SelectTargetGUI(Player player, TargetGUI targetGUI) {
        super(player, "select-target", "Select a target", 6);
        this.targetGui = targetGUI;
        this.callbackType = CallbackType.GUI;
    }

    public SelectTargetGUI(Player player, Consumer<Player> callback) {
        super(player, "select-target", "Select a target", 6);
        this.callback = callback;
        this.callbackType = CallbackType.RUNNABLE;
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

        this.targetPlayer = target;

        String clickedItemDisplayName = clickedItemMeta.getDisplayName();

        if(clickedItemDisplayName.equals(Messages.GET_STAFFLIST_NEXT())) {
            this.pagination.goNextPage();
            pagination.update();
            return false;
        }

        if(clickedItemDisplayName.equals(Messages.GET_STAFFLIST_PREVIUS())) {
            this.pagination.goPreviousPage();
            pagination.update();
            return false;
        }

        if(this.callbackType == CallbackType.RUNNABLE){
            this.callback.accept(target);
            return false;
        }

        if(this.callbackType == CallbackType.GUI){
            targetGui.setTarget(target);
            player.closeInventory();

            targetGui.open();
            return false;
        }

        return false;
    }
    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
    }
}
