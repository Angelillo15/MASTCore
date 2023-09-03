package es.angelillo15.mast.api.addons;

import es.angelillo15.mast.api.ILogger;

public class AddonLogger extends ILogger {
    private final ILogger logger;
    private MAStaffAddon<?> addon;

    public AddonLogger(MAStaffAddon<?> addon, ILogger logger) {
        this.addon = addon;
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        logger.info("[" + addon.getDescriptionFile().getName() + "] " + message);
    }

    @Override
    public void warn(String message) {
        logger.warn("[" + addon.getDescriptionFile().getName() + "] " + message);
    }

    @Override
    public void error(String message) {
        logger.error("[" + addon.getDescriptionFile().getName() + "] " + message);
    }

    @Override
    public void debug(String message) {
        logger.debug("[" + addon.getDescriptionFile().getName() + "] " + message);
    }
}
