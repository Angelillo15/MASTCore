package es.angelillo15.mast.api.managers.loader;

import es.angelillo15.mast.api.managers.BukkitManagerExecutor;
import es.angelillo15.mast.api.managers.Manager;
import es.angelillo15.mast.api.managers.ManagerExecutor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import org.reflections.Reflections;

public class ReflectionLoader {
  public static void loadAll() {
    Reflections reflections = new Reflections("es.angelillo15.mast");
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Manager.class);

    for (Class<?> c : annotated) {
      try {
        ManagerExecutor executor = (ManagerExecutor) c.getDeclaredConstructors()[0].newInstance();
        executor.load();
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  public static void loadBukkit() {
    Reflections reflections = new Reflections("es.angelillo15.mast.bukkit");
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(BukkitManagerExecutor.class);

    for (Class<?> c : annotated) {
      try {
        ManagerExecutor executor = (ManagerExecutor) c.getDeclaredConstructors()[0].newInstance();
        executor.load();
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  public static void loadBungee() {
    Reflections reflections = new Reflections("es.angelillo15.mast.bungee");
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(BukkitManagerExecutor.class);

    for (Class<?> c : annotated) {
      try {
        ManagerExecutor executor = (ManagerExecutor) c.getDeclaredConstructors()[0].newInstance();
        executor.load();
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
