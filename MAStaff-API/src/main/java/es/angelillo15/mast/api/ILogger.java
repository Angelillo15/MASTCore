package es.angelillo15.mast.api;

public abstract class ILogger {
    private static ILogger instance;

    public static ILogger getInstance() {
        return instance;
    }

    public static void setInstance(ILogger instance) {
        ILogger.instance = instance;
    }

    abstract public void info(String message);

    abstract public void warn(String message);

    abstract public void error(String message);

    abstract public void debug(String message);

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
