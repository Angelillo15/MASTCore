package es.angelillo15.mast.bungee.punishments;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.angelillo15.mast.api.punishments.Punishment;
import es.angelillo15.mast.api.punishments.PunishmentsPlayer;
import es.angelillo15.mast.api.punishments.PunishmentsTypes;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

public class PunishmentPlayer extends PunishmentsPlayer<ProxiedPlayer> {
    Cache<Integer, Punishment> punishmentCache = Caffeine.newBuilder()
            .maximumSize(100)
            .build();
    private final ProxiedPlayer player;

    public PunishmentPlayer(ProxiedPlayer player) {
        this.player = player;
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
    public String getIP() {
        return this.player.getAddress().getAddress().getHostAddress().split(":")[0];
    }

    @Override
    public Cache<Integer, Punishment> getPunishments() {
        return punishmentCache;
    }

    @Override
    public ArrayList<Punishment> getPunishments(PunishmentsTypes type) {
        ArrayList<Punishment> punishments = new ArrayList<>();
        punishmentCache.asMap().forEach((id, punishment) -> {
            if (punishment.getType() == type) {
                punishments.add(punishment);
            }
        });
        return punishments;
    }

    @Override
    public ArrayList<Punishment> getPunishments(PunishmentsTypes type, boolean active) {
        ArrayList<Punishment> punishments = new ArrayList<>();
        punishmentCache.asMap().forEach((id, punishment) -> {
            if (punishment.getType() == type) {
                if (active) {
                    if (!punishment.isExpired()) {
                        punishments.add(punishment);
                    }
                } else {
                    punishments.add(punishment);
                }
            }
        });

        return punishments;
    }

    @Override
    public ArrayList<Punishment> getPunishments(boolean active) {
        ArrayList<Punishment> punishments = new ArrayList<>();
        punishmentCache.asMap().forEach((id, punishment) -> {
            if (active) {
                if (!punishment.isExpired()) {
                    punishments.add(punishment);
                }
            } else {
                punishments.add(punishment);
            }
        });
        return punishments;
    }

    @Override
    public boolean isBanned() {
        return false;
    }

    @Override
    public boolean isMuted() {
        return false;
    }

    public void addPunishment(Punishment punishment) {
        punishmentCache.put(punishment.getId(), punishment);
    }
}