package es.angelillo15.mast.bukkit.gui;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.managers.LegacyStaffPlayersManagers;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.StaffPlayer;
import es.angelillo15.mast.api.config.bukkit.Config;
import lombok.SneakyThrows;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StaffVault extends Gui {
    private int page = 0;
    private StaffPlayer staffPlayer;
    public StaffVault(@NotNull Player player, int page) {
        super(player, "StaffVault", TextUtils.colorize(Config.StaffVault.name()), 6);
        this.page = page;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();


        if (!LegacyStaffPlayersManagers.isStaffPlayer(player)) {
            player.closeInventory();
            return;
        }

        staffPlayer = (StaffPlayer) LegacyStaffPlayersManagers.getStaffPlayer(player);

        List<ItemStack> items = staffPlayer.getStaffVault();

        if (items == null) {
            items = new ArrayList<>();
        }

        items.forEach(itemStack -> {
            addItem(new Icon(itemStack));
        });

    }

    @Override
    public boolean onClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();

        if (inv == null) {
            return false;
        }

        ItemStack clickedItem = inv.getItem(event.getSlot());

        if (clickedItem == null) {
            return false;
        }

        if (inv.getType() != InventoryType.CHEST) {

            return clickedItem.getType() == Material.AIR;
        }


        return true;
    }

    private void handlePagination(Pagination pagination) {
        if (pagination == Pagination.NEXT) {
            this.player.closeInventory();
            new StaffVault(this.player, this.page + 1).open();
        } else {
            this.player.closeInventory();
            new StaffVault(this.player, this.page - 1).open();
        }
    }

    @SneakyThrows
    @Override
    public void onClose(InventoryCloseEvent event) {
        staffPlayer.getPlayerInventoryConfig().set("staffVault", null);
        List<ItemStack> items = new ArrayList<>();

        addItem(45, XMaterial.AIR.parseItem());
        addItem(53, XMaterial.AIR.parseItem());

        event.getInventory().forEach(itemStack -> {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                items.add(itemStack);
            }
        });

        staffPlayer.getPlayerInventoryConfig().set("staffVault", items);

        staffPlayer.getPlayerInventoryConfig().save(staffPlayer.getPlayerInventoryFile());

    }

    enum Pagination {
        PREVIOUS,
        NEXT
    }
}
