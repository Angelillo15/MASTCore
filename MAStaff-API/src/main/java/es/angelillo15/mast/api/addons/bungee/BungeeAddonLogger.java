package es.angelillo15.mast.api.addons.bungee;

import es.angelillo15.mast.api.ILogger;
import net.md_5.bungee.api.ProxyServer;

import java.util.logging.Logger;

public class BungeeAddonLogger implements ILogger {
    private MAStaffBungeeAddon addon;
    private Logger logger;

    public BungeeAddonLogger(MAStaffBungeeAddon addon) {
        this.addon = addon;
        this.logger = ProxyServer.getInstance().getPluginManager().getPlugin("MAStaff").getLogger();
    }
    @Override
    public void info(String message) {
        logger.info("[" + addon.getDescriptionFile().getName() + "] " + message);
    }

    @Override
    public void warn(String message) {
        logger.warning("[" + addon.getDescriptionFile().getName() + "] " + message);
    }

    @Override
    public void error(String message) {
        logger.severe("[" + addon.getDescriptionFile().getName() + "] " + message);
    }

    @Override
    public void debug(String message) {
        // TODO: Implement debug
    }
}
