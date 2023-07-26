package es.angelillo15.mast.api.cmd.sender;

import es.angelillo15.mast.api.TextUtils;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;

public class PlayerCommandSender implements CommandSender {
    private final Player player;
    private final Audience audience;
    public PlayerCommandSender(Player player) {
        this.player = player;
        this.audience = TextUtils.getAudience(player);
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
    @Override
    public String getUniqueId() {
        return player.getUniqueId().toString();
    }

    @Override
    public String getAddress() {
        return player.getAddress().getAddress().getHostAddress().split(":")[0];
    }

    @Override
    public Audience getAudience() {
        return audience;
    }

    @Override
    public String getServerName() {
        return player.getServer().getName();
    }
}
