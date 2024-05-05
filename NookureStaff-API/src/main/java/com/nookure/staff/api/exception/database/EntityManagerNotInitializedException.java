package com.nookure.staff.api.exception.database;

public class EntityManagerNotInitializedException extends DatabaseException {
  public EntityManagerNotInitializedException() {
    super("[Hibernate] EntityManager could not be initialized!", "");
  }
}