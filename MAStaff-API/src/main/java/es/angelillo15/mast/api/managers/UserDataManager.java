package es.angelillo15.mast.api.managers;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.data.UserData;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserDataManager {
    private static final Cache<String, UserData> userDataCache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public static UserData getUserData(String key) {
        long start = System.currentTimeMillis();

        boolean exists = userDataCache.asMap().containsKey(key);
        UserData data;

        if (exists) {
            data = userDataCache.getIfPresent(key);
        } else {
            boolean isUUID = key.length() == 36;

            UserData userData = isUUID ?
                    DataManager.getDataManager().getUserData(UUID.fromString(key)) :
                    DataManager.getDataManager().getUserData(key);

            userDataCache.put(key, userData);

            data = userData;
        }

        long end = System.currentTimeMillis();
        MAStaffInstance.getLogger().debug("getUserData took " + (end - start) + "ms");
        return data;
    }

    public static UserData getUserData(UUID UUID) {
        return getUserData(UUID.toString());
    }


    public static void updateUserData(String key, UserData userData) {
        userDataCache.put(key, userData);
    }

    public static void updateUserData(UUID UUID, UserData userData) {
        updateUserData(UUID.toString(), userData);
    }

    public static void removeUserData(String key) {
        userDataCache.invalidate(key);
    }

    public static void removeUserData(UUID UUID) {
        removeUserData(UUID.toString());
    }


    public static boolean userExists(String key) {
        return userDataCache.asMap().containsKey(key);
    }

    public static boolean userExists(UUID UUID) {
        return userExists(UUID.toString());
    }

    public static void clearCache() {
        userDataCache.invalidateAll();
    }
}
