package es.angelillo15.mast.api;

import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import es.angelillo15.mast.api.items.StaffItem;
import lombok.NonNull;
import org.bukkit.Bukkit;
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

}
