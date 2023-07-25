package es.angelillo15.mast.api.cmd.sender;

import com.velocitypowered.api.proxy.Player;
import es.angelillo15.mast.api.TextUtils;
import net.kyori.adventure.audience.Audience;

public class VelocityPlayerCommandSender implements CommandSender {
    private final Player player;

    public VelocityPlayerCommandSender(Player player) {
        this.player = player;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(TextUtils.toComponent(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public String getUniqueId() {
        return player.getUniqueId().toString();
    }

    @Override
    public String getAddress() {
        return player.getRemoteAddress().getAddress().getHostAddress().split(":")[0];
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

    @Override
    public Audience getAudience() {
        return player;
    }
}
