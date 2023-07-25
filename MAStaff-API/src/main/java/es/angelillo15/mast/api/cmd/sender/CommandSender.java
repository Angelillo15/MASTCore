package es.angelillo15.mast.api.cmd.sender;

import net.kyori.adventure.audience.Audience;

import java.util.UUID;

public interface CommandSender {
    void sendMessage(String message);

    boolean hasPermission(String permission);

    String getName();

    String getUniqueId();

    String getAddress();

    boolean isPlayer();

    boolean isConsole();

    boolean isProxy();

    boolean isBungee();

    boolean isSpigot();

    Audience getAudience();
}
