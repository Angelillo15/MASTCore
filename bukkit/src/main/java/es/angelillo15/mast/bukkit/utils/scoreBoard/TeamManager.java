package es.angelillo15.mast.bukkit.utils.scoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TeamManager {
    ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    Scoreboard scoreboard = scoreboardManager.getMainScoreboard();
    HashMap<ChatColor, Team> teams = new HashMap<>();
    HashMap<String, ChatColor> players = new HashMap<>();

    public TeamManager() {

        teams.put(ChatColor.RED, scoreboard.getTeam("red") == null ? scoreboard.registerNewTeam("red") : scoreboard.getTeam("red"));
        teams.put(ChatColor.BLUE, scoreboard.getTeam("blue") == null ? scoreboard.registerNewTeam("blue") : scoreboard.getTeam("blue"));
        teams.put(ChatColor.GREEN, scoreboard.getTeam("green") == null ? scoreboard.registerNewTeam("green") : scoreboard.getTeam("green"));
        teams.put(ChatColor.YELLOW, scoreboard.getTeam("yellow") == null ? scoreboard.registerNewTeam("yellow") : scoreboard.getTeam("yellow"));
        teams.put(ChatColor.AQUA, scoreboard.getTeam("aqua") == null ? scoreboard.registerNewTeam("aqua") : scoreboard.getTeam("aqua"));
        teams.put(ChatColor.DARK_AQUA, scoreboard.getTeam("dark_aqua") == null ? scoreboard.registerNewTeam("dark_aqua") : scoreboard.getTeam("dark_aqua"));
        teams.put(ChatColor.DARK_BLUE, scoreboard.getTeam("dark_blue") == null ? scoreboard.registerNewTeam("dark_blue") : scoreboard.getTeam("dark_blue"));
        teams.put(ChatColor.DARK_GRAY, scoreboard.getTeam("dark_gray") == null ? scoreboard.registerNewTeam("dark_gray") : scoreboard.getTeam("dark_gray"));
        teams.put(ChatColor.DARK_GREEN, scoreboard.getTeam("dark_green") == null ? scoreboard.registerNewTeam("dark_green") : scoreboard.getTeam("dark_green"));
        teams.put(ChatColor.DARK_PURPLE, scoreboard.getTeam("dark_purple") == null ? scoreboard.registerNewTeam("dark_purple") : scoreboard.getTeam("dark_purple"));
        teams.put(ChatColor.DARK_RED, scoreboard.getTeam("dark_red") == null ? scoreboard.registerNewTeam("dark_red") : scoreboard.getTeam("dark_red"));
        teams.put(ChatColor.GOLD, scoreboard.getTeam("gold") == null ? scoreboard.registerNewTeam("gold") : scoreboard.getTeam("gold"));
        teams.put(ChatColor.GRAY, scoreboard.getTeam("gray") == null ? scoreboard.registerNewTeam("gray") : scoreboard.getTeam("gray"));
        teams.put(ChatColor.LIGHT_PURPLE, scoreboard.getTeam("light_purple") == null ? scoreboard.registerNewTeam("light_purple") : scoreboard.getTeam("light_purple"));
        teams.put(ChatColor.WHITE, scoreboard.getTeam("white") == null ? scoreboard.registerNewTeam("white") : scoreboard.getTeam("white"));
        teams.put(ChatColor.BLACK, scoreboard.getTeam("black") == null ? scoreboard.registerNewTeam("black") : scoreboard.getTeam("black"));
        teams.forEach((color, team) -> {
            team.setColor(color);
        });
    }

    public void addPlayer(String player, ChatColor color) {
        players.put(player, color);
        teams.get(color).addEntry(Bukkit.getPlayer(player).getName());
        Bukkit.getPlayer(player).setScoreboard(scoreboard);
    }

    public void removePlayer(String player) {
        if (players.containsKey(player)) {
            teams.get(players.get(player)).removeEntry(player);
            players.remove(player);
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void clear() {
        for (String player : players.keySet()) {
            teams.get(players.get(player)).removeEntry(player);
        }
        players.clear();
    }

    public void enableGlow(Player player){
        player.setGlowing(true);
    }

    public void disableGlow(Player player){
        player.setGlowing(false);
    }

}
