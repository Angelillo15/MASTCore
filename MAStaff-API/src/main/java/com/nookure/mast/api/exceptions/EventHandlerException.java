package com.nookure.mast.api.exceptions;

public class EventHandlerException extends RuntimeException {
  public EventHandlerException(String message) {
    super(message);
  }

  public EventHandlerException(String message, Throwable cause) {
    super(message, cause);
  }
}
