package es.angelillo15.mast.bungee.punishments.listeners;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.punishments.Punishment;
import es.angelillo15.mast.api.punishments.PunishmentsTypes;
import es.angelillo15.mast.api.punishments.cache.PunishmentsManager;
import es.angelillo15.mast.api.punishments.data.AbstractDataManager;
import es.angelillo15.mast.api.punishments.models.BanModel;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import java.util.UUID;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PreLoginEvent event) {
        PendingConnection conn = event.getConnection();


        if (cacheCheck(conn)) return;
        databaseCheck(conn);
    }

    private boolean cacheCheck(PendingConnection conn) {
        MAStaffInstance.getLogger().debug("Checking cache for " + conn.getName() + "'s ban");

        if (!PunishmentsManager.isPunished(conn.getName())) {
            MAStaffInstance.getLogger().debug("Not in cache");
            return false;
        }

        Punishment punishment = PunishmentsManager.getPunishment(conn.getName());

        if (!(punishment.getType() == PunishmentsTypes.BAN)) {
            MAStaffInstance.getLogger().debug("Not banned");
            return false;
        }

        if (punishment.isPermanent()) {
            MAStaffInstance.getLogger().debug("Permanent ban");
            conn.disconnect(new TextComponent(punishment.getReason()));
            return true;
        }

        return false;
    }

    private boolean databaseCheck(PendingConnection player) {
        MAStaffInstance.getLogger().debug("Checking database for " + player.getName() + "'s ban");

        UserData userData = null;

        userData = DataManager.getDataManager().getUserData(player.getName());

        AbstractDataManager manager =
                es.angelillo15.mast.api.punishments.data.DataManager.getDataManager();

        if (manager.isPermBanned(userData.getUUID())) {

            BanModel model = manager.getBan(UUID.fromString(userData.getUUID()));
            player.disconnect(new TextComponent("Banned"));

            PunishmentsManager.addPunishment(player.getName(), new Punishment(
                    PunishmentsTypes.BAN,
                    player.getName(),
                    model.getBannedBy(),
                    model.getReason(),
                    0,
                    -1
            ));
            return true;
        }

        return false;

    }

}
