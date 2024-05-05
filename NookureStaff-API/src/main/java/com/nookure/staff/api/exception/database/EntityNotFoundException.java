package com.nookure.staff.api.exception.database;

public class EntityNotFoundException extends DatabaseException {
  public EntityNotFoundException(Class<?> type) {
    super("[Hibernate] " + type.getName() + " could not be found!", "");
  }
}
