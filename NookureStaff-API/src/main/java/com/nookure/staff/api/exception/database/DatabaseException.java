package com.nookure.staff.api.exception.database;

public class DatabaseException extends Exception {

  private final String detailedMessage;

  public DatabaseException(String message, String detailedMessage) {
    super(message);
    this.detailedMessage = detailedMessage;
  }

  public DatabaseException(String message, Throwable cause) {
    super(message);
    this.initCause(cause);
    this.detailedMessage = cause.getMessage();
  }

  private String getDetailedMessage() {
    return detailedMessage;
  }

  @Override
  public String getMessage() {
    return this.getDetailedMessage();
  }
}