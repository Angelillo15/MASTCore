package com.nookure.staff.sync;

import com.google.inject.Inject;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.event.sync.PacketSentMessage;
import com.nookure.staff.api.event.sync.Redis2ServerQuestion;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.sync.PacketContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

public class RedisMessageBroker extends BaseMessageBroker {
  private final JedisPool jedisPool;
  private final EventMessenger eventMessenger;
  private final Logger logger;
  private static final int TIMEOUT = 10;

  @Inject
  public RedisMessageBroker(
      @NotNull final JedisPool jedisPool,
      @NotNull final Logger logger,
      @NotNull final EventMessenger eventMessenger
  ) {
    this.jedisPool = jedisPool;
    this.logger = logger;
    this.eventMessenger = eventMessenger;
  }

  @Override
  public void sendPacket(byte @NotNull [] key, byte @NotNull [] packet) {
    try (var jedis = jedisPool.getResource()) {
      jedis.publish(key, packet);
    } catch (Exception e) {
      logger.severe("Failed to send packet to Redis", e);
    }
  }

  @Override
  public <T extends Redis2ServerQuestion> T sendPacketAndWait(byte @NotNull [] key, @Nullable UUID server, byte @NotNull [] packet, @NotNull Class<T> responseClass) {
    try (var jedis = jedisPool.getResource()) {
      UUID packetUUID = UUID.randomUUID();
      PacketContainer packetContainer = new PacketContainer(packet, packetUUID, responseClass);

      jedis.rpush(key, packetContainer.serialize());

      if (server != null) {
        logger.debug("Publishing packet sent message");
        eventMessenger.publish(null, new PacketSentMessage(server, key));
      }

      var replyKey = generateReplyKey(packetUUID);

      logger.debug("Waiting for response on key " + new String(replyKey));
      var response = jedis.blpop(TIMEOUT, replyKey);

      if (response == null) {
        logger.severe("Timeout while waiting for response");
        throw new RuntimeException("Timeout while waiting for response");
      }

      return decodePacket(response.get(1), responseClass);
    } catch (Exception e) {
      logger.severe("Failed to send packet to Redis", e);
      throw new RuntimeException(e);
    }
  }
}
