package es.angelillo15.mast.velocity.utils;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.velocity.MAStaff;

public class Logger implements ILogger {
    private final org.slf4j.Logger logger;

    public Logger() {
        this.logger = MAStaff.getInstance().getSlf4jLogger();
    }

    private static final String prefix = "[MAStaff] ";

    @Override
    public void info(String message) {
        MAStaff.getInstance().getProxyServer().sendMessage(TextUtils.toComponent(prefix + message));
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void debug(String message) {
        if (MAStaff.getInstance().isDebug()) {
            info("[DEBUG] " + message);
        }
    }
}
