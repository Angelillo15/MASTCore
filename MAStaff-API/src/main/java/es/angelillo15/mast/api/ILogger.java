package es.angelillo15.mast.api;

public interface ILogger {
    void info(String message);

    void warn(String message);

    void error(String message);

    void debug(String message);

    default void info(String message, Object... args) {
        info(format(message, args));
    }

    default void warn(String message, Object... args) {
        warn(format(message, args));
    }

    default void error(String message, Object... args) {
        error(format(message, args));
    }

    default void debug(String message, Object... args) {
        debug(format(message, args));
    }

    default String format(String message, Object... args) {
        String msg = message;
        for (Object arg : args) {
            msg = msg.replaceFirst("\\{\\}", arg.toString());
        }

        return msg;
    }
}
