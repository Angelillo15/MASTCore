package com.nookure.staff.sync;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.sync.MessageBroker;
import com.nookure.staff.api.sync.PacketContainer;
import com.nookure.staff.sync.module.RedisModule;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@Testcontainers
public class RedisPacketTest extends RedisBaseTest {
  private static final UUID SERVER_1_UUID = UUID.randomUUID();
  private static final UUID SERVER_2_UUID = UUID.randomUUID();

  @Test
  public void sendPacket() {
    Thread server1 = new Thread(this::server1);
    server1.start();
  }


  public void server1() {
    Injector injector = Guice.createInjector(new RedisModule(jedisPool, SERVER_1_UUID));
    MessageBroker broker = injector.getInstance(MessageBroker.class);
    EventManager eventManager = injector.getInstance(EventManager.class);

    eventManager.registerListener(injector.getInstance(RedisMessageBrokerListener.class));
  }

  public void server2() {
    Injector injector = Guice.createInjector(new RedisModule(jedisPool, SERVER_2_UUID));
    MessageBroker broker = injector.getInstance(MessageBroker.class);

  }
}

