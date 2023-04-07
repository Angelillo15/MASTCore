package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.bungee.MAStaff;

public class Logger implements ILogger {
    @Override
    public void info(String message) {
        MAStaff.getInstance().getPluginInstance().getLogger().info(message);
    }

    @Override
    public void warn(String message) {
        MAStaff.getInstance().getPluginInstance().getLogger().warning(message);
    }

    @Override
    public void error(String message) {
        MAStaff.getInstance().getPluginInstance().getLogger().severe(message);
    }

    @Override
    public void debug(String message) {
        if (MAStaff.getInstance().isDebug()) {
            MAStaff.getInstance().getPluginInstance().getLogger().info("[DEBUG] " + message);
        }
    }
}
