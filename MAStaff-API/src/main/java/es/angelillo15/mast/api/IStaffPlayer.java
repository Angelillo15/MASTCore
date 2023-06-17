package es.angelillo15.mast.api;

import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import es.angelillo15.mast.api.items.StaffItem;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;

/**
* This interface allows to you to interact with the staff players
*/
public interface IStaffPlayer {

    /**
     * toggle the staff mode
     */
    void toggleStaffMode();

    /**
     * toggle the staff mode
     */
    void toggleStaffMode(boolean saveInventory);

    /**
     * @return boolean
     * Returns true if the player is in StaffMode
     */
    boolean isStaffMode();

    /**
     * @param staffMode enable or disable the staff mode
     * Method to enable or disable the StaffMode,
     * ths method can throw exceptions
     */
    void setStaffMode(boolean staffMode, boolean saveInventory) throws AlreadyEnableException, AlreadyDisableException;
    /**
     * Toggle the vanish mode
     */
    void toggleVanish();

    /**
     * @return boolean
     * Returns true if the p
     */
    boolean isVanished();

    /**
     * @return the player
     * Method to get the Player under StaffPlayer
     */
    Player getPlayer();

    /**
     * Set staff items to the player
     */
    public void setItems();

    /**
     * Get staff items
     */
    public Map<String, StaffItem> getItems();

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

    /**
     * Get the staff mode previus status
     * @return the staff mode previus status
     */
    public boolean wasInStaffMode();

    /**
     * Is a checker that if there is a new item that isn't
     * of the staff mode inventory, it will be removed
     * and added to a staff mode vault
     */
    void staffModeAsyncInventoryChecker();

    /**
     * Get staff vault
     * @return the staff vault
     */
    List<ItemStack> getStaffVault();

    /**
     * Open staff vault
     */
    void openStaffVault();
}
