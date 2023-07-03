package es.angelillo15.mast.api.punishments.history;

import es.angelillo15.mast.api.punishments.IPunishPlayer;

public abstract class HistoryProvider {
    /**
     * Send the history of the player to the sender
     * @param sender The sender
     * @param page The page of the history
     */
    public abstract void sendHistory(IPunishPlayer sender, int page);
}
