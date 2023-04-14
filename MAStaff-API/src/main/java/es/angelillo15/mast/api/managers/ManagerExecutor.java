package es.angelillo15.mast.api.managers;

public interface ManagerExecutor {
    void load();
    default void unload() {

    };
    default void reload() {

    };
}
