package es.angelillo15.mast.bukkit.api.events.staff;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;


public class FreezeMessageEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private boolean isCancelled;
    private ArrayList<String> messages;

    public FreezeMessageEvent(Player player) {
        super(true);
        this.player = player;
        this.isCancelled = false;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }
}
