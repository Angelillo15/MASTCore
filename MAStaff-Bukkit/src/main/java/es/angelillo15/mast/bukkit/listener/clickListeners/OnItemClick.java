package es.angelillo15.mast.bukkit.listener.clickListeners;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.items.IExecutableItem;
import es.angelillo15.mast.api.items.IExecutableLocationItem;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class OnItemClick implements Listener {
    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(!player.hasPermission(Permissions.STAFF.getPermission())){
            event.setCancelled(false);
            return;
        }

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(!staffPlayer.isStaffMode()){
            event.setCancelled(false);
            return;
        }

        if(event.getItem() == null){
            if(!player.hasPermission(Permissions.STAFF_BUILD.getPermission())){
                event.setCancelled(true);
            }
            return;
        }

        ItemMeta meta = event.getItem().getItemMeta();

        staffPlayer.getItems().forEach(item -> {
            if(item.getItem().getItemMeta().getDisplayName().equals(meta.getDisplayName())){
                if(item instanceof IExecutableItem) {
                    MAStaff.getPlugin().getPLogger().debug("Executing item " +
                            item.getItem().getItemMeta().getDisplayName() +
                            " for player " + player.getName()
                    );
                    ((IExecutableItem) item).click(player);
                }

                if(item instanceof IExecutableLocationItem){
                    if(event.getClickedBlock() == null) return;

                    MAStaff.getPlugin().getPLogger().debug("Executing item " +
                            item.getItem().getItemMeta().getDisplayName() +
                            " for player " + player.getName()
                    );

                    ((IExecutableLocationItem) item).click(player, event.getClickedBlock().getLocation());
                }
            }
        });

        event.setCancelled(true);
    }
}
