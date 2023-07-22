package es.angelillo15.mast.velocity.utils;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.velocity.MAStaff;

public class Logger extends ILogger {
    private final org.slf4j.Logger logger;

    public Logger() {
        this.logger = MAStaff.getInstance().getSlf4jLogger();
        setInstance(this);
    }

    private static final String prefix = "[MAStaff] ";

    @Override
    public void info(String message) {
        logger.info(TextUtils.simpleColorize(message));
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
