package es.angelillo15.mast.bukkit.listener.clickListeners;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.items.IPlayerInteractItem;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class OnItemClickInteract implements Listener {
    static boolean clicked = false;
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if(!player.hasPermission(Permissions.STAFF.getPermission())) return;

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()) {
            event.setCancelled(true);
        }

        if(player.getItemInHand() == null) return;
        if(player.getItemInHand().getItemMeta() == null) return;
        if(player.getItemInHand().getItemMeta().getDisplayName() == null) return;
        if(event.getRightClicked() == null) return;
        if(!(event.getRightClicked() instanceof Player)) return;

        Player target = (Player) event.getRightClicked();

        staffPlayer.getItems().forEach(item -> {
            if(item.getItem().getItemMeta().getDisplayName().equals(player.getItemInHand().getItemMeta().getDisplayName())) {
                if(item instanceof IPlayerInteractItem){
                    MAStaff.getPlugin().getPLogger().debug("Executing item " +
                            item.getItem().getItemMeta().getDisplayName() +
                            " for player " + player.getName()
                    );
                    if(clicked) {
                        clicked = false;
                        return;
                    }

                    IPlayerInteractItem interactItem = (IPlayerInteractItem) item;

                    interactItem.interact(player, target);

                    clicked = true;
                }

            }
        });

    }
}
