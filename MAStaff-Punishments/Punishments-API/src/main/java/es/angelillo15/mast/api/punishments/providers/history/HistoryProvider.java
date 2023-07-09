package es.angelillo15.mast.api.punishments.providers.history;

import es.angelillo15.mast.api.punishments.IPunishPlayer;
import lombok.Getter;
import lombok.Setter;

public abstract class HistoryProvider {
    @Getter
    @Setter
    private static HistoryProvider instance;

    /**
     * Send the history of the player to the sender
     * @param sender The sender
     * @param page The page of the history
     */
    public abstract void sendHistory(IPunishPlayer sender, int page);
}
