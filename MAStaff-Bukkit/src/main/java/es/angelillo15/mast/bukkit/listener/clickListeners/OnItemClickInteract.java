package es.angelillo15.mast.bukkit.listener.clickListeners;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.items.IPlayerInteractItem;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

@SuppressWarnings({"deprecation", "ConstantValue"})
public class OnItemClickInteract implements Listener {
    static boolean clicked = false;
    @Inject
    private StaffManager staffManager;

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!staffManager.isStaffPlayer(player)) return;
        if (!player.hasPermission(Permissions.STAFF.getPermission())) return;

        IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

        if (staffPlayer.isStaffMode()) {
            event.setCancelled(true);
        }

        if (player.getItemInHand() == null) return;
        if (player.getItemInHand().getItemMeta() == null) return;
        if (player.getItemInHand().getItemMeta().getDisplayName() == null) return;
        if (event.getRightClicked() == null) return;
        if (!(event.getRightClicked() instanceof Player)) return;

        Player target = (Player) event.getRightClicked();

        StaffItem item = staffPlayer.getItems().get(player.getItemInHand().getItemMeta().getDisplayName());
        if (item == null) return;

        if (item instanceof IPlayerInteractItem) {
            MAStaff.getPlugin().getPLogger().debug("Executing item " +
                    item.getItem().getItemMeta().getDisplayName() +
                    " for player " + player.getName()
            );
            if (clicked) {
                clicked = false;
                return;
            }

            IPlayerInteractItem interactItem = (IPlayerInteractItem) item;

            interactItem.interact(player, target);

            clicked = true;
        }

    }
}
