package es.angelillo15.mast.api.punishments.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.angelillo15.mast.api.punishments.Punishment;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class PunishmentsManager {
    @Getter
    private static final Cache<String, Punishment> punishmentCache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public static void addPunishment(String usr, Punishment punishment) {
        punishmentCache.put(usr, punishment);
    }

    public static Punishment getPunishment(String usr) {
        return punishmentCache.getIfPresent(usr);
    }

    public static void removePunishment(String usr) {
        punishmentCache.invalidate(usr);
    }

    public static boolean isPunished(String usr) {
        return punishmentCache.getIfPresent(usr) != null;
    }
}
