package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.gui.GuiUtils;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.MAStaff;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.pagination.PaginationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffListGUI extends Gui {
  private final PaginationManager pagination = new PaginationManager(this);

  public StaffListGUI(Player player) {
    super(player, "staff-list", Messages.GET_STAFFLIST_TITLE(), 6);
  }

  @Override
  public void onOpen(InventoryOpenEvent event) {
    MAStaff.getPlugin().getPLogger().debug("StaffListGUI opened");
    for (Player people : Bukkit.getOnlinePlayers()) {
      if (people.hasPermission("mast.staff")) {
        ItemStack item = GuiUtils.getPlayerHead(people, Messages.GET_STAFFLIST_LORE());
        pagination.addItem(new Icon(item));
      }
    }

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

    MAStaff.getPlugin().getPLogger().debug("StaffListGUI items added");
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

    if (clickedItemDisplayName.equals(Messages.GET_STAFFLIST_NEXT())) {
      this.pagination.goNextPage();
      pagination.update();
      return false;
    }

    if (clickedItemDisplayName.equals(Messages.GET_STAFFLIST_PREVIUS())) {
      this.pagination.goPreviousPage();
      pagination.update();
      return false;
    }

    MAStaff.getPlugin().getPLogger().debug("StaffListGUI clicked");

    return false;
  }
}
