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
          This should be in the format of `host:port`.
          For example, `localhost:6379`.
              """
  )
  private String address = "localhost:6379";

  @Setting
  @Comment(
      """
          The password to use when connecting to the Redis server.
          If the server does not require a password, leave this empty.
              """
  )
  private String password = "";

  @Setting
  @Comment(
      """
          The database to use when connecting to the Redis server.
          This should be a number between 0 and 15.
              """
  )
  private int database = 0;

  public String getAddress() {
    return address;
  }

  public String getPassword() {
    return password;
  }

  public int getDatabase() {
    return database;
  }
}
