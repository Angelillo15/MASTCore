package es.angelillo15.mast.api.cmd.sender;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxiedPlayerCommandSender implements CommandSender {
    private final ProxiedPlayer player;

    public ProxiedPlayerCommandSender(ProxiedPlayer player) {
        this.player = player;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(new TextComponent(message));
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
        return true;
    }

    @Override
    public boolean isBungee() {
        return true;
    }

    @Override
    public boolean isSpigot() {
        return false;
    }
}
