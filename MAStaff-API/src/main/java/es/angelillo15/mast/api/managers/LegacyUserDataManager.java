package es.angelillo15.mast.api.managers;

import com.google.inject.Inject;
import es.angelillo15.mast.api.models.UserModel;

import java.util.UUID;

@Deprecated
public class LegacyUserDataManager {
    @Inject
    private static UserDataManager userManager;

    public static UserModel getUserData(String key) {
       return userManager.getUserData(key);
    }

    public static UserModel getUserData(UUID UUID) {
        return getUserData(UUID.toString());
    }

    public static void removeUserData(String key) {
        userManager.removeUserData(key);
    }

    public static void removeUserData(UUID UUID) {
        removeUserData(UUID.toString());
    }
}
