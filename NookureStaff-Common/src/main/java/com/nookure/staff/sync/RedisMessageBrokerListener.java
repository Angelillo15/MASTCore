package com.nookure.staff.sync;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.annotation.server.ServerUUID;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.event.NookSubscribe;
import com.nookure.staff.api.event.sync.PacketSentMessage;
import com.nookure.staff.api.event.sync.Redis2ServerQuestion;
import com.nookure.staff.api.sync.PacketContainer;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class RedisMessageBrokerListener {
  private final AtomicReference<UUID> serverUUID;
  private final RedisMessageBroker redisMessageBroker;
  private final JedisPool jedisPool;
  private final Logger logger;
  private final EventManager eventManager;

  @Inject
  public RedisMessageBrokerListener(
      @NotNull @ServerUUID final AtomicReference<UUID> serverUUID,
      @NotNull final RedisMessageBroker redisMessageBroker,
      @NotNull final EventManager eventManager,
      @NotNull final JedisPool jedisPool,
      @NotNull final Logger logger
  ) {
    this.serverUUID = serverUUID;
    this.redisMessageBroker = redisMessageBroker;
    this.jedisPool = jedisPool;
    this.eventManager = eventManager;
    this.logger = logger;
  }

  @NookSubscribe
  public void onPacketSentMessage(PacketSentMessage packetSentMessage) {
    if (!packetSentMessage.server().equals(serverUUID.get())) {
      return;
    }

    logger.debug("Received packet sent message");

    try (var jedis = jedisPool.getResource()) {
      var packet = PacketContainer.deserialize(jedis.lpop(packetSentMessage.key()));

      Class<? extends Redis2ServerQuestion> responseClass = packet.responseClass();
      logger.debug("Received packet with response class " + responseClass.getName());

      try {
        Redis2ServerQuestion event = redisMessageBroker.decodePacket(packet.packet(), packet.responseClass());
        event = eventManager.fireEvent(event).join();

        jedis.rpush(redisMessageBroker.generateReplyKey(packet.packetUUID()), event.serialize());
        logger.debug("""
            Package request has been processed and response has been sent
              key: %s
              response: %s
            """, packetSentMessage.key(), event.getClass().getName());
      } catch (Exception e) {
        logger.severe("Failed to decode packet", e);
      }

    } catch (IOException e) {
      logger.severe("Failed to deserialize packet", e);
    }
  }
}
