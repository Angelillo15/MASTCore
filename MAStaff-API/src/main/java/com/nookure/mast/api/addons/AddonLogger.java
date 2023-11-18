package com.nookure.mast.api.addons;

import es.angelillo15.mast.api.ILogger;

public class AddonLogger extends ILogger {
  private final String prefix;
  private final ILogger logger;

  public AddonLogger(String prefix, ILogger logger) {
    this.prefix = prefix;
    this.logger = logger;
  }

  @Override
  public void info(String message) {
    logger.info("[" + prefix + "] " + message);
  }

  @Override
  public void warn(String message) {
    logger.warn("[" + prefix + "] " + message);
  }

  @Override
  public void error(String message) {
    logger.error("[" + prefix + "] " + message);
  }

  @Override
  public void debug(String message) {
    logger.debug("[" + prefix + "] " + message);
  }
}
