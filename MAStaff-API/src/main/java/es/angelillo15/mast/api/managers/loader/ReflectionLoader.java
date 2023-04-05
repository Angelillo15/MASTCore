package es.angelillo15.mast.api.managers.loader;

import es.angelillo15.mast.api.managers.Manager;
import es.angelillo15.mast.api.managers.ManagerExecutor;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ReflectionLoader {
    public static void loadAll(Class<?> clazz){
        Reflections reflections = new Reflections("es.angelillo15.mast");
        Set<Class<?>> anottated = reflections.getTypesAnnotatedWith(Manager.class);

        for (Class<?> c : anottated) {
            try {
                ManagerExecutor executor = (ManagerExecutor) c.getDeclaredConstructors()[0].newInstance();
                executor.load();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
