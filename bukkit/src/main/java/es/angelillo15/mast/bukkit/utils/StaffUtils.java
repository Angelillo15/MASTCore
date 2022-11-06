package es.angelillo15.mast.bukkit.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.angelillo15.mast.bukkit.MASTBukkit;
import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishDisableEvent;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.utils.items.InternalModules;
import es.angelillo15.mast.database.SQLQueries;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Random;

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
            MASTBukkitManager.getInstance().getServer().getScheduler().runTaskAsynchronously(MASTBukkitManager.getInstance(), () -> {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.sendMessage(Messages.GET_VANISH_LEAVE_MESSAGE().replace("{player}", player.getName()));
                });
            });
        } else {
            disableStaff(player);
            MASTBukkitManager.getInstance().getServer().getScheduler().runTaskAsynchronously(MASTBukkitManager.getInstance(), () -> {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.sendMessage(Messages.GET_VANISH_JOIN_MESSAGE().replace("{player}", player.getName()));
                });
            });
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

        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(true);

        Inventory.saveInventory(player);

        StaffUtils.loadItems(player);

        sendStaffData(player, true);

        player.sendMessage(Messages.GET_STAFF_MODE_ENABLE_MESSAGE());

        if(MASTBukkit.serverVersion >= 9){
            if(ConfigLoader.getGlowModule().getConfig().getString("mode").equalsIgnoreCase("vault")){
                String group = MASTBukkitManager.getPerms().getPrimaryGroup(player);
                ChatColor color = ChatColor.BLUE;
                String colorStr = ConfigLoader.getGlowModule().getConfig().getString("Config.groups." + group + ".color");

                if(colorStr != null){
                    color = ChatColor.valueOf(colorStr);
                }

                MASTBukkitManager.getTeamManager().addPlayer(player.getName(), color);
                MASTBukkitManager.getTeamManager().enableGlow(player);
            }
        }
    }

    public static void disableStaff(Player player) {
        MASTBukkitManager plugin = MASTBukkitManager.getInstance();
        if (plugin.containsStaffPlayer(player.getUniqueId())) {
            plugin.removeStaffPlayer(plugin.getSStaffPlayer(player.getUniqueId()));
        }
        if (SQLQueries.getState(plugin.getPluginConnection().getConnection(), player.getUniqueId()) == 1) {
            pm.callEvent(new PlayerVanishDisableEvent(player));
        }

        player.setGameMode(GameMode.SURVIVAL);

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
        InternalModules internalModules = new InternalModules();

        for (StaffItem item : internalModules.getItems()) {
            item.set(player);
        }
    }


    public static void playerRandomTeleport(Player player){
        ArrayList<Player> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (!(p.hasPermission("mast.staff")) || !(p.equals(player))) {
                players.add(p);
            }
        });
        if(players.isEmpty()){
            player.sendMessage(Messages.GET_NO_PLAYER_ONLINE_MESSAGE());
            return;
        }
        final Random random = new Random();
        player.teleport(players.get(random.nextInt(players.size())).getLocation());
    }

    public static void asyncStaffBroadcastMessage(String message){
        Bukkit.getScheduler().runTaskAsynchronously(MASTBukkitManager.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                if(p.hasPermission("mast.staff")){
                    p.sendMessage(message);
                }
            });
            MASTBukkitManager.getInstance().getLogger().info(message);
        });
    }

    public static void asyncStaffChatMessage(String message){
        Bukkit.getScheduler().runTaskAsynchronously(MASTBukkitManager.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                if(p.hasPermission("mast.staffchat")){
                    p.sendMessage(message);
                }
            });
            MASTBukkitManager.getInstance().getLogger().info(message);
        });
    }

    public static String getPlayerCount(Player player){
        Boolean type = MASTBukkitManager.getInstance().getConfig().getBoolean("Config.bungeecord");
        if(!type){
            return String.valueOf(Bukkit.getOnlinePlayers().size());
        } else {
            return MASTBukkitManager.getOnlinePlayers().toString();
        }
    }
}
