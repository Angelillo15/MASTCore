package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {
    @Inject
    private StaffManager staffManager;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!staffManager.isStaffPlayer(player)) return;

        IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

        if (staffPlayer == null) return;

        if (staffPlayer.isStaffMode()) event.setCancelled(true);
    }
}
