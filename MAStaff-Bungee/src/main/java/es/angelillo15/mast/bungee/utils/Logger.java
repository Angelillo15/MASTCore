package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.api.ILogger;
import com.nookure.mast.bungee.MAStaff;

public class Logger extends ILogger {
    public Logger() {
        setInstance(this);
    }
    @Override
    public void info(String message) {
        MAStaff.getInstance().getLogger().info(message);
    }

    @Override
    public void warn(String message) {
        MAStaff.getInstance().getLogger().warning(message);
    }

    @Override
    public void error(String message) {
        MAStaff.getInstance().getLogger().severe(message);
    }

    @Override
    public void debug(String message) {
        if (MAStaff.getInstance().isDebug()) {
            MAStaff.getInstance().getLogger().info("[DEBUG] " + message);
        }
    }
}
