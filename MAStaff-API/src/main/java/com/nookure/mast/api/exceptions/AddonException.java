package com.nookure.mast.api.exceptions;

public class AddonException extends RuntimeException {
  public AddonException(String message) {
    super(message);
  }

  public AddonException(String message, Throwable cause) {
    super(message, cause);
  }
}
