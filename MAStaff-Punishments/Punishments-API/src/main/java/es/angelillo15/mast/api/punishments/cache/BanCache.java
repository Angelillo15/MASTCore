package es.angelillo15.mast.api.punishments.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.angelillo15.mast.api.models.BanModel;
import es.angelillo15.mast.api.punishments.config.Config;
import es.angelillo15.mast.api.punishments.data.migrations.BansTable;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class BanCache {
    @Getter
    private static final Cache<String, BansTable> punishmentCache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(Config.cacheRefreshTime(), TimeUnit.SECONDS)
            .build();

    public static void addPunishment(String usr, BansTable ban) {
        punishmentCache.put(usr, ban);
    }

    public static BansTable getPunishment(String usr) {
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
