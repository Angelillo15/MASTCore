package es.angelillo15.mast.api;

public abstract class ILogger {
  private static ILogger instance;

  public static ILogger getInstance() {
    return instance;
  }

  public static void setInstance(ILogger instance) {
    ILogger.instance = instance;
  }

  public abstract void info(String message);

  public abstract void warn(String message);

  public abstract void error(String message);

  public abstract void debug(String message);

  void info(String message, Object... args) {
    info(format(message, args));
  }

  void warn(String message, Object... args) {
    warn(format(message, args));
  }

  void error(String message, Object... args) {
    error(format(message, args));
  }

  void debug(String message, Object... args) {
    debug(format(message, args));
  }

  String format(String message, Object... args) {
    String msg = message;
    for (Object arg : args) {
      msg = msg.replaceFirst("\\{\\}", arg.toString());
    }

    return msg;
  }
}
