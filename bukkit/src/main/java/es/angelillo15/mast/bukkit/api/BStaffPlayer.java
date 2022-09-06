package es.angelillo15.mast.bukkit.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BStaffPlayer {
    private final String player;
    private boolean isInStaffMode;
    private boolean isVanished;
    private boolean canSeeVanished;
    private boolean isInStaffChat;
    private boolean canBypassRestrictions;

    public BStaffPlayer(String playerName) {
        this.player = playerName;
        this.isInStaffMode = false;
        this.isVanished = false;
        this.canSeeVanished = false;
        this.isInStaffChat = false;
        this.canBypassRestrictions = false;
    }

    public BStaffPlayer(Player player) {
        this.player = player.getUniqueId().toString();
        this.isInStaffMode = false;
        this.isVanished = false;
        this.canSeeVanished = false;
        this.isInStaffChat = false;
        this.canBypassRestrictions = false;
    }

    public BStaffPlayer(UUID playerUUID) {
        this.player = playerUUID.toString();
        this.isInStaffMode = false;
        this.isVanished = false;
        this.canSeeVanished = false;
        this.isInStaffChat = false;
        this.canBypassRestrictions = false;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(player));
    }

    public boolean getStaffMode() {
        return this.isInStaffMode;
    }

    public void setStaffMode(boolean inStaffMode) {
        this.isInStaffMode = inStaffMode;
    }

    public boolean isVanished() {
        return isVanished;
    }

    public void setVanished(boolean vanished) {
        this.isVanished = vanished;
    }

    public boolean CanSeeVanished() {
        return canSeeVanished;
    }

    public void setCanSeeVanished(boolean canSeeVanished) {
        this.canSeeVanished = canSeeVanished;
    }

    public boolean isInStaffChat() {
        return isInStaffChat;
    }

    public void setInStaffChat(boolean inStaffChat) {
        this.isInStaffChat = inStaffChat;
    }

    public boolean CanBypassRestrictions() {
        return canBypassRestrictions;
    }

    public void setBypassRestrictions(boolean canBypassRestrictions) {
        this.canBypassRestrictions = canBypassRestrictions;
    }
}
