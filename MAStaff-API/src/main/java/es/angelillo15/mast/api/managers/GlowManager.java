package es.angelillo15.mast.api.managers;

import es.angelillo15.glow.data.glow.Glow;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class GlowManager {
    @Getter
    private static HashMap<String, Glow> glows = new HashMap<>();

    /**
     * @param name the name of the glow
     * @param glow the glow
     */
    public static void addGlow(String name, Glow glow){
        glows.put(name, glow);
    }

    /**
     * @param name the name of the glow to get
     * @return the glow
     */
    public static Glow getGlow(String name){
        return glows.get(name);
    }

    /**
     * Get all glows
     * @return ArrayList<Glow>
     */
    public static ArrayList<Glow> getGlowList(){
        return new ArrayList<>(glows.values());
    }

    /**
     * Remove a glow from the list
     * @param name the name of the glow
     */
    public static void removeGlow(String name){
        glows.remove(name);
    }

    /**
     * clear all glows
     */
    public static void clearGlowList(){
        glows.clear();
    }

    /**
     * @param name the name of the glow
     * @return true if the glow exists or false if not
     */
    public static boolean containsGlow(String name){
        return glows.containsKey(name);
    }
}
