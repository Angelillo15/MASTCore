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
     * @param color the name of the glow to get
     * @return the glow
     */
    public static Glow getGlow(ChatColor color){
        return glows.get(color);
    }

    /**
     * Get all glows
     * @return ArrayList of glows
     */
    public static ArrayList<Glow> getGlowList(){
        return new ArrayList<>(glows.values());
    }

    /**
     * clear all glows
     */
    public static void clearGlowList(){
        glows.clear();
    }

    /**
     * @param color to delete
     */
    public static void removeGlow(ChatColor color) {
        glows.remove(color);
    }

    @Getter
    private static HashMap<String, ChatColor> colors = new HashMap<>();

    /**
     * @param group the name of the color
     * @param color the color
     */
    public static void addColor(String group, ChatColor color) {
        colors.put(group, color);
    }

    /**
     * @param group the name of the color to get
     * @return the color
     */
    public static ChatColor getColor(String group) {
        return colors.get(group) == null ? ChatColor.GREEN : colors.get(group);
    }

    /**
     * Get all colors
     * @return ArrayList
     */
    public static ArrayList<ChatColor> getColorList() {
        return new ArrayList<>(colors.values());
    }

    /**
     * clear all colors
     */
    public static void clearColorList() {
        colors.clear();
    }

    /**
     * @param group to delete
     */
    public static void removeColor(String group) {
        colors.remove(group);
    }
}
