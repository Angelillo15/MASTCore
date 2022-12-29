package es.angelillo15.mast.api.player;

import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import lombok.NonNull;
import org.bukkit.entity.Player;

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

    Player getPlayer();



}
