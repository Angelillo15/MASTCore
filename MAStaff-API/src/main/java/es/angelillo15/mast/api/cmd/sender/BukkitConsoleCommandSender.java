package es.angelillo15.mast.api.cmd.sender;

import es.angelillo15.mast.api.MAStaffInstance;

public class BukkitConsoleCommandSender implements CommandSender {
    @Override
    public void sendMessage(String message) {
        MAStaffInstance.getLogger().info(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public String getUniqueId() {
        return "CONSOLE";
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isConsole() {
        return true;
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
    public String getAddress() {
        return "0.0.0.0";
    }
}
