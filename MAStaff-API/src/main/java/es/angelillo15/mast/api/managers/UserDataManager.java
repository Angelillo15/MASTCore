package es.angelillo15.mast.api.managers;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.DataManager;
import es.angelillo15.mast.api.models.UserModel;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class UserDataManager {
  private final Cache<String, UserModel> userDataCache =
      Caffeine.newBuilder().maximumSize(100).expireAfterWrite(1, TimeUnit.MINUTES).build();

  /**
   * Get user data from the database. If the user data is not in the cache, it will be retrieved
   * from the database. If the user data is not in the database, it will return empty user data with
   * the UUID or username set, and it Will be filled with the rest when the user joins for the first
   * time.
   *
   * @param key UUID or username
   * @return UserModel
   */
  public UserModel getUserData(String key) {
    long start = System.currentTimeMillis();

    boolean exists = userDataCache.asMap().containsKey(key);
    UserModel data;

    if (exists) {
      data = userDataCache.getIfPresent(key);
    } else {
      boolean isUUID = key.length() == 36;

      UserModel userModel =
          isUUID
              ? DataManager.getDataManager().getUserData(UUID.fromString(key))
              : DataManager.getDataManager().getUserData(key);

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

      MAStaffInstance.getLogger().debug("User " + key + " doesn't exist in the database.");
      MAStaffInstance.getLogger().debug(" - UUID: " + userModel.getUUID());
      MAStaffInstance.getLogger().debug(" - Username: " + userModel.getUsername());
      MAStaffInstance.getLogger().debug(" - Username Length: " + userModel.getUsername().length());
      MAStaffInstance.getLogger().debug(" - IP: " + userModel.getLastIp());
      MAStaffInstance.getLogger().debug(" - First join: " + userModel.getFirstLogin());

      userDataCache.put(key, userModel);

      data = userModel;
    }

    long end = System.currentTimeMillis();
    MAStaffInstance.getLogger().debug("getUserData took " + (end - start) + "ms");

    if (data == null) {
      MAStaffInstance.getLogger().debug("User " + key + " has no data.");
      MAStaffInstance.getLogger().debug(" - Key: " + key);
      MAStaffInstance.getLogger().debug(" - Key Length: " + key.length());
    }

    return data;
  }

  /**
   * Get user data from the database. If the user data is not in the cache, it will be retrieved
   * from the database. If the user data is not in the database, it will return empty user data with
   * the UUID set, and it Will be filled with the rest when the user joins for the first time.
   *
   * @param uuid UUID of the user
   * @return UserModel
   */
  public UserModel getUserData(UUID uuid) {
    return getUserData(uuid.toString());
  }

  /**
   * Remove user data from the cache.
   *
   * @param key UUID or username
   */
  public void removeUserData(String key) {
    userDataCache.invalidate(key);
  }

  /**
   * Remove user data from the cache.
   *
   * @param UUID UUID of the user
   */
  public void removeUserData(UUID UUID) {
    removeUserData(UUID.toString());
  }

  /** Clear the cache. */
  public void clearCache() {
    userDataCache.invalidateAll();
  }
}
