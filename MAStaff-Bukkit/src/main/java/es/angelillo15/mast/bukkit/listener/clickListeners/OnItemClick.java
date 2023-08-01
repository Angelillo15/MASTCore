package es.angelillo15.mast.bukkit.listener.clickListeners;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.items.IExecutableItem;
import es.angelillo15.mast.api.items.IExecutableLocationItem;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnItemClick implements Listener {
    @Inject
    private StaffManager staffManager;

    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFF.getPermission())) {
            return;
        }

        IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

        if (!staffPlayer.isStaffMode()) {
            return;
        }

        if (event.getItem() == null) {

            if (!player.hasPermission(Permissions.STAFF_BUILD.getPermission())) {
                event.setCancelled(true);
            }
            return;
        }

        StaffItem item = staffPlayer.getItems().get(player.getItemInHand().getItemMeta().getDisplayName());

        if (item instanceof IExecutableItem) {
            MAStaff.getPlugin().getPLogger().debug("Executing item " +
                    item.getItem().getItemMeta().getDisplayName() +
                    " for player " + player.getName()
            );
            ((IExecutableItem) item).click(player);
        }

        if (item instanceof IExecutableLocationItem) {
            if (event.getClickedBlock() == null) return;

            MAStaff.getPlugin().getPLogger().debug("Executing item " +
                    item.getItem().getItemMeta().getDisplayName() +
                    " for player " + player.getName()
            );

            ((IExecutableLocationItem) item).click(player, event.getClickedBlock().getLocation());
        }

        event.setCancelled(true);
    }
}
