package es.angelillo15.mast.api.punishments.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.angelillo15.mast.api.punishments.models.BanModel;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class BanCache {
    @Getter
    private static final Cache<String, BanModel> punishmentCache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public static void addPunishment(String usr, BanModel ban) {
        punishmentCache.put(usr, ban);
    }

    public static BanModel getPunishment(String usr) {
        return punishmentCache.getIfPresent(usr);
    }

    public static void removePunishment(String usr) {
        punishmentCache.invalidate(usr);
    }

    public static boolean isPunished(String usr) {
        return punishmentCache.getIfPresent(usr) != null;
    }

    public static void clearCache() {
        punishmentCache.invalidateAll();
    }
}
