package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class StaffUtils {
    public static void playerRandomTeleport(Player player) {
        ArrayList<Player> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (!(p.hasPermission("mast.staff")) || !(p.equals(player))) {
                players.add(p);
            }
        });
        if (players.isEmpty()) {
            player.sendMessage(Messages.GET_NO_PLAYER_ONLINE_MESSAGE());
            return;
        }
        final Random random = new Random();
        player.teleport(players.get(random.nextInt(players.size())).getLocation());
    }
}
