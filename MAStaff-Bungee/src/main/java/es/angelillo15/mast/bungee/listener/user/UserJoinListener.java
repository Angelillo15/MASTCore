package es.angelillo15.mast.bungee.listener.user;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.managers.UserDataManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class UserJoinListener implements Listener {
    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onUserJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        boolean userExists = DataManager.getDataManager().userExists(player.getUniqueId());

        MAStaffInstance.getLogger().debug("User " + player.getUniqueId() + " joined the server and was registered in the database.");

        UserDataManager.removeUserData(player.getUniqueId());
        UserDataManager.removeUserData(player.getName());

        if (userExists) {
            new Thread(() -> {
                UserData userData = DataManager.getDataManager().getUserData(player.getUniqueId());

                DataManager.getDataManager().updateIP(player.getUniqueId().toString(), player.getAddress().getAddress().getHostAddress());
                DataManager.getDataManager().updateLastLogin(player.getUniqueId().toString(), String.valueOf(System.currentTimeMillis()));

                if (!userData.getUsername().equals(player.getName())) {
                    DataManager.getDataManager().updateUsername(player.getUniqueId().toString(), player.getName());
                }
            }).start();

        } else {
            DataManager.getDataManager().insertUserData(
                    player.getUniqueId().toString(),
                    player.getName(),
                    player.getAddress().getAddress().getHostAddress(),
                    player.getAddress().getAddress().getHostAddress(),
                    String.valueOf(System.currentTimeMillis()),
                    String.valueOf(System.currentTimeMillis())
            );
        }

        MAStaffInstance.getLogger().debug("User " + player.getName() + " joined the server and was registered in the database.");

    }
}
