package com.nookure.staff.api.config.messaging;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class MessengerConfig {
  @Setting
  @Comment(
      """
          The configuration for the Redis messenger.
              """
  )
  public RedisPartial redis = new RedisPartial();
  @Setting
  @Comment(
      """
          The type of messenger to use.
          Here are the options:
          - REDIS: Use Redis to send messages between servers (recommended)
          - PM: Use plugin messages to send messages between servers this
          will only work if all the servers are under the same proxy and
          in the proxy is installed the bridge plugin.
          - NONE: Disable the sync, useful when you don't have a network
          and you only have 1 server.
              """
  )
  private MessengerType type = MessengerType.NONE;

  public MessengerType getType() {
    return type;
  }

  public enum MessengerType {
    REDIS,
    PM,
    NONE
  }
}
