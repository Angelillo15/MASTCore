package es.angelillo15.mast.bukkit.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishDisableEvent;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import es.angelillo15.mast.bukkit.api.item.StaffItem;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.utils.items.InternalModules;
import es.angelillo15.mast.database.SQLQueries;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.simpleyaml.configuration.file.YamlFile;

public class StaffUtils {
    private static PluginManager pm = MASTBukkitManager.getInstance().getServer().getPluginManager();

    public static void toggleStaff(Player player) {
        MASTBukkitManager plugin = MASTBukkitManager.getInstance();

        if (!(SQLQueries.existsData(MASTBukkitManager.getInstance().getPluginConnection().getConnection(),
                player.getUniqueId()))) {
            SQLQueries.insertData(MASTBukkitManager.getInstance().getPluginConnection().getConnection()
                    , player.getUniqueId(), 0, 0);
        }

        if (SQLQueries.getState(plugin.getPluginConnection().getConnection(), player.getUniqueId()) == 0) {
            enableStaff(player);
        } else {
            disableStaff(player);
        }

    }

    public static void enableStaff(Player player) {
        MASTBukkitManager plugin = MASTBukkitManager.getInstance();
        plugin.addStaffPlayer(new BStaffPlayer(player));
        if (!(plugin.containsStaffPlayer(player.getUniqueId()))) {
            plugin.addStaffPlayer(new BStaffPlayer(player));
        }

        if (SQLQueries.getVanished(plugin.getPluginConnection().getConnection(), player.getUniqueId()) != 1) {
            pm.callEvent(new PlayerVanishEnableEvent(player));
        }



        Inventory.saveInventory(player);

        StaffUtils.loadItems(player);

        sendStaffData(player, true);

        player.sendMessage(Messages.GET_STAFF_MODE_ENABLE_MESSAGE());
    }

    public static void disableStaff(Player player) {
        MASTBukkitManager plugin = MASTBukkitManager.getInstance();
        if (plugin.containsStaffPlayer(player.getUniqueId())) {
            plugin.removeStaffPlayer(plugin.getSStaffPlayer(player.getUniqueId()));
        }
        if (SQLQueries.getState(plugin.getPluginConnection().getConnection(), player.getUniqueId()) == 1) {
            pm.callEvent(new PlayerVanishDisableEvent(player));
        }



        sendStaffData(player, false);

        Inventory.restoreInventory(player);
        player.sendMessage(Messages.GET_STAFF_MODE_DISABLE_MESSAGE());
    }

    public static void sendStaffData(Player player, Boolean state) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("mast");
        out.writeUTF(player.getName());
        out.writeUTF(state.toString());

        player.sendPluginMessage(MASTBukkitManager.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void loadItems(Player player) {
        InternalModules internalModules = new InternalModules(player);

        for (StaffItem item : internalModules.getItems()) {
            item.set();
        }
    }
}
