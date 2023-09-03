package es.angelillo15.mast.api.inject;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.MembersInjector;
import java.lang.reflect.Field;

public class StaticMembersInjector<T> implements MembersInjector<T> {
  private final Injector injector;

  public StaticMembersInjector(Injector injector) {
    this.injector = injector;
  }

  public static <T> void injectStatics(Injector injector, Class<T> clazz) {
    Field[] fields = clazz.getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(Inject.class)) {
        try {
          field.setAccessible(true);
          field.set(null, injector.getInstance(field.getType()));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void injectMembers(T instance) {
    injector.injectMembers(instance);
  }
}
