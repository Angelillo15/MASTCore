package com.nookure.staff.api.util;

import com.nookure.staff.api.Logger;
import org.jetbrains.annotations.NotNull;

public final class ExceptionUtil {
  public static void warningCallStack(@NotNull final Thread thread, @NotNull final Logger logger) {
    StackTraceElement[] stackTrace = thread.getStackTrace();

    logger.warning("--------------------------------- DEBUG CALL STACK ---------------------------------");
    for (StackTraceElement element : stackTrace) {
      logger.warning(element.toString());
    }
  }
}
