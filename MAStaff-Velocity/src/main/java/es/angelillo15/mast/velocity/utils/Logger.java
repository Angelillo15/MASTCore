package es.angelillo15.mast.velocity.utils;

import com.velocitypowered.api.proxy.ProxyServer;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.velocity.MAStaff;

public class Logger extends ILogger {
  private static final String prefix = "[MAStaff] ";
  private final org.slf4j.Logger logger;
  private final ProxyServer proxyServer;

  public Logger() {
    this.logger = MAStaff.getInstance().getSlf4jLogger();
    this.proxyServer = MAStaff.getInstance().getProxyServer();
    setInstance(this);
  }

  @Override
  public void info(String message) {
    proxyServer.getConsoleCommandSource().sendMessage(TextUtils.toComponent(prefix + message));
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
