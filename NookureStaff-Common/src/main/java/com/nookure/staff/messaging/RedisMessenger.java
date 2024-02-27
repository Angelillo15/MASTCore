package com.nookure.staff.messaging;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.annotation.RedisPublish;
import com.nookure.staff.api.annotation.RedisSubscribe;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.util.Scheduler;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;

import java.util.Arrays;

@Singleton
public class RedisMessenger extends EventMessenger {
  @Inject
  @RedisPublish
  private Jedis jedisPublish;
  @Inject
  @RedisSubscribe
  private Jedis jedisSubscribe;
  @Inject
  private Injector injector;
  @Inject
  private Logger logger;
  @Inject
  private Scheduler scheduler;
  private BinaryJedisPubSub messenger;
  private static final byte[] CHANNEL = "nkstaff_events".getBytes();

  private int subscribeTaskID;

  @Override
  public void prepare() {
    if (!jedisPublish.isConnected()) {
      logger.debug("Connecting to redis publish...");
      jedisPublish.connect();
    }

    if (!jedisSubscribe.isConnected()) {
      logger.debug("Connecting to redis subscribe...");
      jedisSubscribe.connect();
    }

    logger.debug("Subscribing to redis channel " + Arrays.toString(CHANNEL));
    messenger = injector.getInstance(RedisEventMessenger.class);
    subscribeTaskID = scheduler.async(() -> jedisSubscribe.subscribe(messenger, CHANNEL));
  }

  @Override
  @SuppressWarnings("SynchronizeOnNonFinalField")
  public void publish(@NotNull PlayerWrapper sender, byte @NotNull [] data) {
    synchronized (jedisPublish) {
      logger.debug("Publishing event to redis");
      jedisPublish.publish(CHANNEL, data);
    }
  }

  private static class RedisEventMessenger extends BinaryJedisPubSub {
    @Inject
    private EventMessenger eventTransport;
    @Inject
    private EventManager eventManager;
    @Inject
    private Logger logger;

    @Override
    public void onSubscribe(byte[] channel, int subscribedChannels) {
      logger.debug("Subscribed to redis channel " + Arrays.toString(channel));
    }

    @Override
    public void onUnsubscribe(byte[] channel, int subscribedChannels) {
      logger.debug("Unsubscribed from redis channel " + Arrays.toString(channel));
    }

    @Override
    public void onMessage(byte[] channel, byte[] message) {
      try {
        logger.debug("Received message from redis");
        eventTransport.decodeEvent(message).ifPresent(event -> {
          logger.debug("Received event " + event.getClass().getSimpleName() + " from redis");
          eventManager.fireEvent(event);
        });
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void close() throws Exception {
    messenger.unsubscribe();
    scheduler.cancel(subscribeTaskID);

    jedisSubscribe.close();
    jedisPublish.close();
  }
}
