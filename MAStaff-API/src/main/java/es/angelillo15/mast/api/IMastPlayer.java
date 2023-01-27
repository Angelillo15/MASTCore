package es.angelillo15.mast.api;

import es.angelillo15.mast.api.report.Report;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface IMastPlayer {
    /**
     * Get the player
     * @return Player
     */
    public Player getPlayer();

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