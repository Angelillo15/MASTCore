package com.nookure.staff.messaging;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.messaging.EventMessenger;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

@Singleton
public class RedisMessenger extends EventMessenger {
  @Inject
  private Jedis jedis;
  @Inject
  private Injector injector;

  private static final byte[] CHANNEL = "nookure:staff:events".getBytes(StandardCharsets.UTF_8);

  @Override
  public void prepare() {
    if (!jedis.isConnected()) {
      jedis.connect();
    }

    jedis.subscribe(injector.getInstance(RedisEventMessenger.class), CHANNEL);
  }

  @Override
  public void publish(@NotNull PlayerWrapper sender, byte @NotNull [] data) {
    jedis.publish(CHANNEL, data);
  }

  private static class RedisEventMessenger extends BinaryJedisPubSub {
    @Inject
    private EventMessenger eventTransport;
    @Inject
    private EventManager eventManager;
    @Inject
    private Logger logger;

    @Override
    public void onMessage(byte[] channel, byte[] message) {
      try {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(message);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        eventTransport.decodeEvent(objectInputStream).ifPresent(event -> {
          logger.debug("Received event " + event.getClass().getSimpleName() + " from plugin message");
          eventManager.fireEvent(event);
        });
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
