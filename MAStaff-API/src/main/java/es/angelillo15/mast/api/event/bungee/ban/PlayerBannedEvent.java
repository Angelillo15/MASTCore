package es.angelillo15.mast.api.event.bungee.ban;

import com.nookure.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.models.BansTable;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.event.AsyncEvent;

public class PlayerBannedEvent extends AsyncEvent<PlayerBannedEvent> {
    private static BansTable banModel;
    private static CommandSender player;
    public PlayerBannedEvent(BansTable model, CommandSender player, Callback<PlayerBannedEvent> done) {
        super(done);
        banModel = model;
        player = player;
    }

    /**
     * Get the ban model of the banned player
     * @return The ban model of the banned player
     */
    public BansTable getBanModel() {
        return banModel;
    }

    /**
     * Get the banned player
     * @return The banned player
     */
    public CommandSender getPlayer() {
        return player;
    }
}
