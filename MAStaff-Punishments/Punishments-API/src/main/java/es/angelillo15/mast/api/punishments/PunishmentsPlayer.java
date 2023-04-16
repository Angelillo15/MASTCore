package es.angelillo15.mast.api.punishments;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.ArrayList;

public abstract class PunishmentsPlayer<P> {
    /**
     * Get player
     */
    public abstract P getPlayer();

    /**
     * Get player name
     */
    public abstract String getName();

    /**
     * Get player UUID
     */
    public abstract String getUUID();

    /**
     * Get player IP
     */
    public abstract String getIP();

    /**
     * Get punishments
     */
    public abstract Cache<Integer, Punishment> getPunishments();

    /**
     * Get punishments
     * @param type Punishment type
     */
    public abstract ArrayList<Punishment> getPunishments(PunishmentsTypes type);

    /**
     * Get punishments
     * @param type Punishment type
     * @param active Only active punishments
     */
    public abstract  ArrayList<Punishment> getPunishments(PunishmentsTypes type, boolean active);

    /**
     * Get punishments
     * @param active Only active punishments
     */
    public abstract ArrayList<Punishment> getPunishments(boolean active);

    /**
     * Is player banned
     */
    public abstract boolean isBanned();

    /**
     * Is player muted
     */
    public abstract boolean isMuted();
}
