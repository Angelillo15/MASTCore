package es.angelillo15.mast.api.punishments;

public class Punishment {
    private final PunishmentsTypes type;
    private final String player;
    private final String punisher;
    private final String reason;
    private final long time;
    private final long duration;
    private int id;

    public Punishment(PunishmentsTypes type, String player, String punisher, String reason, long time, long duration) {
        this.type = type;
        this.player = player;
        this.punisher = punisher;
        this.reason = reason;
        this.time = time;
        this.duration = duration;
    }

    public PunishmentsTypes getType() {
        return type;
    }

    public String getPlayer() {
        return player;
    }

    public String getPunisher() {
        return punisher;
    }

    public String getReason() {
        return reason;
    }

    public long getTime() {
        return time;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isPermanent() {
        return duration == -1;
    }

    public boolean isExpired() {
        return !isPermanent() && System.currentTimeMillis() > time + duration;
    }

    public boolean isTemp() {
        return !isPermanent() && !isExpired();
    }

    public long getRemainingTime() {
        return time + duration - System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }
}
