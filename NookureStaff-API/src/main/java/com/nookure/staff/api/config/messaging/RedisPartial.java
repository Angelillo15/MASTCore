package com.nookure.staff.api.config.messaging;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class RedisPartial {
  @Setting
  @Comment(
      """
          The address of the Redis server.
          This should be in the format of `host`.
          For example, `localhost`.
          """
  )
  private final String address = "localhost";

  @Setting
  @Comment(
      """
          The port of the Redis server.
          This should be a number between 1 and 65535.
          """
  )
  private final int port = 6379;

  @Setting
  @Comment(
      """
          The password to use when connecting to the Redis server.
          If the server does not require a password, leave this empty.
          """
  )
  private final String password = "";

  @Setting
  @Comment(
      """
          The database to use when connecting to the Redis server.
          This should be a number between 0 and 15.
          """
  )
  private final int database = 0;

  @Setting
  @Comment(
      """
          The pool size to use when connecting to the Redis server.
          """
  )
  private final int poolSize = 10;

  @Setting
  @Comment(
      """
          The timeout to use when connecting to the Redis server.
          """
  )
  private final int timeout = 2000;

  public String getAddress() {
    return address;
  }

  public String getPassword() {
    return password;
  }

  public int getPort() {
    return port;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public int getTimeout() {
    return timeout;
  }

  public int getDatabase() {
    return database;
  }
}
