package es.angelillo15.mast.glow;

import es.angelillo15.mast.api.exceptions.NoPlaceholderDataException;

import java.util.HashMap;

public class PlaceholderManager {
    private static HashMap<String, Placeholder> placeholders = new HashMap<>();

    /**
     * Register a placeholder
     * @param placeholder Placeholder to register
     */
    public static void registerPlaceholder(Placeholder placeholder) {
        PlaceholderData data = placeholder.getClass().getAnnotation(PlaceholderData.class);

        if (data == null) {
            throw new NoPlaceholderDataException("PlaceholderData annotation not found in " + placeholder.getClass().getName());
        }

        placeholders.put(data.key(), placeholder);
    }

    /**
     * Get a placeholder
     * @param key Placeholder key
     * @return Placeholder
     */
    public static Placeholder getPlaceholder(String key) {
        return placeholders.get(key);
    }

    /**
     * Get all placeholders
     * @return All placeholders
     */
    public static HashMap<String, Placeholder> getPlaceholders() {
        return placeholders;
    }

    /**
     * Unregister a placeholder
     * @param key Placeholder key
     */
    public static void unregisterPlaceholder(String key) {
        placeholders.remove(key);
    }
}
