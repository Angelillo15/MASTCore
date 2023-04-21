package es.angelillo15.mast.api.cmd.sender;

import org.bukkit.entity.Player;

public class PlayerCommandSender implements CommandSender {
    private final Player player;
    public PlayerCommandSender(Player player) {
        this.player = player;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    @Override
    public boolean isBungee() {
        return false;
    }

    @Override
    public boolean isSpigot() {
        return true;
    }
}
