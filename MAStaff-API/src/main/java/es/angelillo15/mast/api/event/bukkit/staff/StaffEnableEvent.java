package es.angelillo15.mast.api.event.bukkit.staff;

import es.angelillo15.mast.api.IStaffPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player enables staff mode
 * @author Angelillo15
 * @since 2.1.0
 */
public class StaffEnableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private IStaffPlayer staffPlayer;

    public StaffEnableEvent(IStaffPlayer staffPlayer) {
        this.staffPlayer = staffPlayer;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Get the staff player who enable staff mode
     * @return IStaffPlayer
     */
    public IStaffPlayer getStaffPlayer() {
        return staffPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
