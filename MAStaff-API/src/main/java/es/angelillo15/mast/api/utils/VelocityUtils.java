package es.angelillo15.mast.api.utils;

import com.velocitypowered.api.proxy.ProxyServer;
import es.angelillo15.mast.api.MAStaffInstance;
import java.lang.reflect.Method;

public class VelocityUtils {
  private static MAStaffInstance<ProxyServer> instance = null;

  public static MAStaffInstance<ProxyServer> getInstance() {
    if (instance != null) {
      return instance;
    }

    Class<?> clazz = null;

    try {
      clazz = Class.forName("es.angelillo15.mast.velocity.MAStaff");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    Method getInstance;

    try {
      assert clazz != null;
      getInstance = clazz.getDeclaredMethod("getInstance");
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }

    try {
      instance = (MAStaffInstance<ProxyServer>) getInstance.invoke(null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return instance;
  }
}
