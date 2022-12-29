package es.angelillo15.mast.api.exceptions;

public class PluginNotLoadedException extends IllegalStateException {
    public PluginNotLoadedException(String message) {
        super(message);
    }
}
