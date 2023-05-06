package es.angelillo15.mast.api.managers;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.models.UserModel;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserDataManager {
    private static final Cache<String, UserModel> userDataCache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public static UserModel getUserData(String key) {
        long start = System.currentTimeMillis();

        boolean exists = userDataCache.asMap().containsKey(key);
        UserModel data;

        if (exists) {
            data = userDataCache.getIfPresent(key);
        } else {
            boolean isUUID = key.length() == 36;

            UserModel userModel = isUUID ?
                    DataManager.getDataManager().getUserData(UUID.fromString(key)) :
                    DataManager.getDataManager().getUserData(key);

            if (userModel == null) {
                MAStaffInstance.getLogger().debug("User " + key + " does not exist in the database.");
                UserModel emptyData = new UserModel();

                if (isUUID) {
                    emptyData.setUUID(key);
                } else {
                    emptyData.setUsername(key);
                }

                return emptyData;
            }

            userDataCache.put(key, userModel);

            data = userModel;
        }

        long end = System.currentTimeMillis();
        MAStaffInstance.getLogger().debug("getUserData took " + (end - start) + "ms");
        return data;
    }

    public static UserModel getUserData(UUID UUID) {
        return getUserData(UUID.toString());
    }


    public static void updateUserData(String key, UserModel userModel) {
        userDataCache.put(key, userModel);
    }

    public static void updateUserData(UUID UUID, UserModel userModel) {
        updateUserData(UUID.toString(), userModel);
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
