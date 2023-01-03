package es.angelillo15.mast.api;

import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import es.angelillo15.mast.api.items.StaffItem;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface IStaffPlayer {

    /**
     * toggle the staff mode
     */
    void toggleStaffMode();

    /**
     * @return true if the player is in staff mode or false if not
     */
    boolean isStaffMode();

    /**
     * @param staffMode enable or disable the staff mode
     */
    void setStaffMode(@NonNull boolean staffMode) throws AlreadyEnableException, AlreadyDisableException;
    /**
     * Toggle the vanish mode
     */
    void toggleVanish();

    /**
     * @return true if the player is vanished or false if not
     */
    boolean isVanished();

    /**
     * @return the player
     */
    Player getPlayer();

    /**
     * Set staff items to the player
     */
    public void setItems();

    /**
     * Get staff items
     */
    public ArrayList<StaffItem> getItems();

    /**
     * Send plugin message to the bungee
     */
    public void sendPluginMessage();

    /**
     * Save inventory
     */
    public void saveInventory();

    /**
     * Clear inventory
     */
    public void clearInventory();

    /**
     * Restore inventory
     */
    public void restoreInventory();

    /**
     * Exists data
     * @return true if exists or false if not
     */
    public boolean existsData();

    /**
     * Change gamemode
     * @param gamemode gamemode to change
     */
    public void changeGamemode(GameMode gamemode);

    /**
     * Set a glow color
     * @param color color to set
     */
    public void setGlowColor(ChatColor color);

    /**
     * Get the glow color
     * @return the glow color
     */
    public ChatColor getGlowColor();

    /**
     * enable / disable glow
     * @param status status to set
     */
    public void setGlowing(boolean status);
}
