package es.angelillo15.mast.bungee;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.player.IMastPlayer;
import es.angelillo15.mast.api.player.PlayerType;
import es.angelillo15.mast.api.report.Report;
import java.util.ArrayList;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MastPlayer implements IMastPlayer<ProxiedPlayer> {
    private ProxiedPlayer player;
    private ArrayList<Report> reports = new ArrayList<>();

    public MastPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.BUNGEE_PLAYER;
    }

    @Override
    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    @Override
    public String getName() {
        return this.player.getName();
    }

    @Override
    public String getUUID() {
        return this.player.getUniqueId().toString();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(new TextComponent(TextUtils.colorize(message)));
    }

    @Override
    public void sendMessages(String... message) {
        for (String s : message) {
            player.sendMessage(new TextComponent(TextUtils.colorize(s)));
        }
    }

    @Override
    public ArrayList<Report> getReports() {
        return this.reports;
    }

    @Override
    public void addReport(Report report) {
        this.reports.add(report);
    }

    @Override
    public void removeReport(Report report) {
        this.reports.remove(report);
    }

    @Override
    public void reportPlayer(IMastPlayer<ProxiedPlayer> playerToReport, String reason) {
        Report report = new Report(this, playerToReport, reason);
        playerToReport.addReport(report);
    }
}
