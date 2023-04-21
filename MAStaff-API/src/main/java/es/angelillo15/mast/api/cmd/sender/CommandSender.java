package es.angelillo15.mast.api.cmd.sender;

public interface CommandSender {
    void sendMessage(String message);

    boolean hasPermission(String permission);

    String getName();

    boolean isPlayer();

    boolean isConsole();

    boolean isProxy();

    boolean isBungee();

    boolean isSpigot();
}
