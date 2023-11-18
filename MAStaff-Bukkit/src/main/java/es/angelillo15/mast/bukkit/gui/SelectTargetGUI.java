package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.gui.GuiUtils;
import es.angelillo15.mast.api.material.XMaterial;

import java.util.function.Consumer;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.pagination.PaginationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class SelectTargetGUI extends Gui {
  private final PaginationManager pagination = new PaginationManager(this);
  private Consumer<Player> callback;

  public SelectTargetGUI(Player player) {
    super(player, "select-target", "Select a target", 6);
  }

  @Override
  public void onOpen(InventoryOpenEvent event) {

    Bukkit.getOnlinePlayers()
        .forEach(
            people -> {
              ItemStack item = GuiUtils.getPlayerHead(people, Messages.GET_STAFFLIST_LORE());
              pagination.addItem(new Icon(item).onClick(e -> callback.accept(people)));
            });

    this.pagination.registerPageSlotsBetween(10, 16);
    this.pagination.registerPageSlotsBetween(19, 25);
    this.pagination.registerPageSlotsBetween(28, 34);
    this.pagination.registerPageSlotsBetween(37, 43);

    this.pagination.update();

    ItemStack previousPage = XMaterial.ARROW.parseItem();
    assert previousPage != null;
    ItemMeta previousPageMeta = previousPage.getItemMeta();
    previousPageMeta.setDisplayName(Messages.GET_STAFFLIST_PREVIUS());
    previousPage.setItemMeta(previousPageMeta);

    ItemStack nextPage = XMaterial.ARROW.parseItem();
    assert nextPage != null;
    ItemMeta nextPageMeta = nextPage.getItemMeta();
    nextPageMeta.setDisplayName(Messages.GET_STAFFLIST_NEXT());
    nextPage.setItemMeta(nextPageMeta);

    addItem(45, new Icon(previousPage).onClick(e -> {
      pagination.goPreviousPage();
      pagination.update();
    }));

    addItem(53, new Icon(nextPage).onClick(e -> {
      pagination.goNextPage();
      pagination.update();
    }));
  }

  @Override
  public void onClose(InventoryCloseEvent event) {
    super.onClose(event);
  }

  public SelectTargetGUI callback(Consumer<Player> callback) {
    this.callback = callback;
    return this;
  }
}
