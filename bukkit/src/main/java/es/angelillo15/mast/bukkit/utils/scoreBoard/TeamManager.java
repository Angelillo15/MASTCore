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
    Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
    HashMap<ChatColor, Team> teams = new HashMap<>();
    HashMap<String, ChatColor> players = new HashMap<>();

    public TeamManager() {
        teams.put(ChatColor.RED, scoreboard.registerNewTeam("red"));
        teams.put(ChatColor.BLUE, scoreboard.registerNewTeam("blue"));
        teams.put(ChatColor.GREEN, scoreboard.registerNewTeam("green"));
        teams.put(ChatColor.YELLOW, scoreboard.registerNewTeam("yellow"));
        teams.put(ChatColor.AQUA, scoreboard.registerNewTeam("aqua"));
        teams.put(ChatColor.DARK_AQUA, scoreboard.registerNewTeam("dark_aqua"));
        teams.put(ChatColor.DARK_BLUE, scoreboard.registerNewTeam("dark_blue"));
        teams.put(ChatColor.DARK_GRAY, scoreboard.registerNewTeam("dark_gray"));
        teams.put(ChatColor.DARK_GREEN, scoreboard.registerNewTeam("dark_green"));
        teams.put(ChatColor.DARK_PURPLE, scoreboard.registerNewTeam("dark_purple"));
        teams.put(ChatColor.DARK_RED, scoreboard.registerNewTeam("dark_red"));
        teams.put(ChatColor.GOLD, scoreboard.registerNewTeam("gold"));
        teams.put(ChatColor.GRAY, scoreboard.registerNewTeam("gray"));
        teams.put(ChatColor.LIGHT_PURPLE, scoreboard.registerNewTeam("light_purple"));
        teams.put(ChatColor.WHITE, scoreboard.registerNewTeam("white"));
        teams.put(ChatColor.BLACK, scoreboard.registerNewTeam("black"));
    }

    public void addPlayer(String player, ChatColor color) {
        players.put(player, color);
        teams.get(color).addEntry(player);
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
