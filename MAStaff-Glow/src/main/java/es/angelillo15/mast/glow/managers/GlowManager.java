package es.angelillo15.mast.glow.managers;

import es.angelillo15.mast.api.chat.api.ChatColor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class GlowManager {
    @Getter
    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    @Getter
    private static Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
    @Getter
    private static HashMap<ChatColor, Team> teams = new HashMap<>();
    @Getter
    private static HashMap<String, ChatColor> players = new HashMap<>();

    public static void setup() {
        teams.put(ChatColor.RED,
                scoreboard.getTeam("red") == null ?
                        scoreboard.registerNewTeam("red") : scoreboard.getTeam("red")
        );
        teams.put(ChatColor.BLUE,
                scoreboard.getTeam("blue") == null ?
                        scoreboard.registerNewTeam("blue") : scoreboard.getTeam("blue")
        );
        teams.put(ChatColor.GREEN,
                scoreboard.getTeam("green") == null ?
                        scoreboard.registerNewTeam("green") : scoreboard.getTeam("green")
        );
        teams.put(ChatColor.YELLOW,
                scoreboard.getTeam("yellow") == null ?
                        scoreboard.registerNewTeam("yellow") : scoreboard.getTeam("yellow")
        );
        teams.put(ChatColor.AQUA,
                scoreboard.getTeam("aqua") == null ?
                        scoreboard.registerNewTeam("aqua") : scoreboard.getTeam("aqua")
        );
        teams.put(ChatColor.DARK_AQUA,
                scoreboard.getTeam("dark_aqua") == null ?
                        scoreboard.registerNewTeam("dark_aqua") : scoreboard.getTeam("dark_aqua")
        );
        teams.put(ChatColor.DARK_BLUE,
                scoreboard.getTeam("dark_blue") == null ?
                        scoreboard.registerNewTeam("dark_blue") : scoreboard.getTeam("dark_blue")
        );
        teams.put(ChatColor.DARK_GRAY,
                scoreboard.getTeam("dark_gray") == null ?
                        scoreboard.registerNewTeam("dark_gray") : scoreboard.getTeam("dark_gray")
        );
        teams.put(ChatColor.DARK_GREEN,
                scoreboard.getTeam("dark_green") == null ?
                        scoreboard.registerNewTeam("dark_green") : scoreboard.getTeam("dark_green")
        );
        teams.put(ChatColor.DARK_PURPLE,
                scoreboard.getTeam("dark_purple") == null ?
                        scoreboard.registerNewTeam("dark_purple") : scoreboard.getTeam("dark_purple")
        );
        teams.put(ChatColor.DARK_RED,
                scoreboard.getTeam("dark_red") == null ?
                        scoreboard.registerNewTeam("dark_red") : scoreboard.getTeam("dark_red")
        );
        teams.put(ChatColor.GOLD,
                scoreboard.getTeam("gold") == null ?
                        scoreboard.registerNewTeam("gold") : scoreboard.getTeam("gold")
        );
        teams.put(ChatColor.GRAY,
                scoreboard.getTeam("gray") == null ?
                        scoreboard.registerNewTeam("gray") : scoreboard.getTeam("gray")
        );
        teams.put(ChatColor.LIGHT_PURPLE,
                scoreboard.getTeam("light_purple") == null ?
                        scoreboard.registerNewTeam("light_purple") : scoreboard.getTeam("light_purple")
        );
        teams.put(ChatColor.WHITE,
                scoreboard.getTeam("white") == null ?
                        scoreboard.registerNewTeam("white") : scoreboard.getTeam("white")
        );
        teams.put(ChatColor.BLACK,
                scoreboard.getTeam("black") == null ?
                        scoreboard.registerNewTeam("black") : scoreboard.getTeam("black")
        );
    }

    public static void addPlayer(String player, ChatColor color) {
        Player p = Bukkit.getPlayer(player);

        if (p == null) return;

        addPlayer(p, color);
    }

    /**
     * Add a player to the scoreboard
     * @param player Player name
     * @param color Color of the player
     */
    public static void addPlayer(Player player, ChatColor color) {
        players.put(player.getName(), color);
        teams.get(color).addEntry(player.getName());

        player.setScoreboard(scoreboard);
    }

    /**
     * Remove a player from the scoreboard
     * @param player Player
     */
    public static void removePlayer(Player player) {
        removePlayer(player.getName());
    }

    /**
     * Remove a player from the scoreboard
     * @param player Player name
     */
    public static void removePlayer(String player) {
        if (players.containsKey(player)) {
            teams.get(players.get(player)).removeEntry(player);
            players.remove(player);
        }
    }

    /**
     * Clear players from the scoreboard
     */
    public static void clear() {
        for (String player : players.keySet()) {
            teams.get(players.get(player)).removeEntry(player);
        }
        players.clear();
    }
}
