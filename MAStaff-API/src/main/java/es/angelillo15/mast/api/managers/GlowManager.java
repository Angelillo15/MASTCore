package es.angelillo15.mast.api.managers;

import es.angelillo15.glow.data.glow.Glow;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;

public class GlowManager {
    @Getter
    private static HashMap<ChatColor, Glow> glows = new HashMap<>();

    /**
     * @param color the color
     * @param glow the glow
     */
    public static void addGlow(ChatColor color, Glow glow){
        glows.put(color, glow);
    }

    /**
     * @param name the name of the glow to get
     * @return the glow
     */
    public static Glow getGlow(ChatColor color){
        return glows.get(color);
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
