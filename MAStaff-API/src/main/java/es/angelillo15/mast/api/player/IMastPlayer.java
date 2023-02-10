package es.angelillo15.mast.api.player;

import es.angelillo15.mast.api.report.Report;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface IMastPlayer {

    /**
     * Get the player's type
     * @return PlayerType
     */
    public PlayerType getPlayerType();

    /**
     * Get the player's name
     * @return String
     */
    public String getName();

    /**
     * Get the player's UUID
     * @return String
     */
    public String getUUID();

    /**
     * Send a message to the player
     * @param message String
     */
    public void sendMessage(String message);

    /**
     * Send messages to the player
     * @param message String
     */
    public void sendMessages(String... message);




    /**
     * Get reports
     */
    public ArrayList<Report> getReports();

    /**
     * Add a report
     * @param report Report
     */
    public void addReport(Report report);

    /**
     * Remove a report
     * @param report Report
     */
    public void removeReport(Report report);

    /**
     * Report a player
     * @param playerToReport IMastPlayer
     * @param reason String
     */
    public void reportPlayer(IMastPlayer playerToReport, String reason);
}