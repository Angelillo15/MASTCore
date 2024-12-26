package com.nookure.staff.sync.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.nookure.staff.api.annotation.server.ServerUUID;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.api.sync.MessageBroker;
import com.nookure.staff.api.util.Scheduler;
import com.nookure.staff.messaging.RedisMessenger;
import com.nookure.staff.sync.RedisMessageBroker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class RedisModule extends AbstractModule {
  private final JedisPool jedisPool;
  private final UUID serverUUID;

  public RedisModule(@NotNull final JedisPool jedisPool, @NotNull final UUID serverUUID) {
    this.jedisPool = jedisPool;
    this.serverUUID = serverUUID;
  }

  @Override
  protected void configure() {
    bind(JedisPool.class).toInstance(jedisPool);

    bind(new TypeLiteral<AtomicReference<UUID>>() {
    }).annotatedWith(ServerUUID.class).toInstance(new AtomicReference<>(serverUUID));

    bind(MessageBroker.class).to(RedisMessageBroker.class).asEagerSingleton();
    bind(Logger.class).toInstance(new Logger());
    bind(EventManager.class).asEagerSingleton();
    bind(EventMessenger.class).to(RedisMessenger.class);
    bind(Scheduler.class).toInstance(new TestScheduler());
  }

  public static class TestScheduler extends Scheduler {
    private Map<Integer, Thread> tasks = new ConcurrentHashMap<>();

    int currentTaskId = 0;

    @Override
    public int async(Runnable runnable, long delay) {
      currentTaskId++;
      tasks.put(currentTaskId, new Thread(runnable));
      tasks.get(currentTaskId).start();
      return currentTaskId;
    }

    @Override
    public int sync(Runnable runnable, long delay) {
      currentTaskId++;
      runnable.run();
      return currentTaskId;
    }

    @Override
    public int async(Runnable runnable, long delay, long period) {
      currentTaskId++;
      tasks.put(currentTaskId, new Thread(runnable));
      tasks.get(currentTaskId).start();
      return currentTaskId;
    }

    @Override
    public int sync(Runnable runnable, long delay, long period) {
      currentTaskId++;
      runnable.run();
      return currentTaskId;
    }

    @Override
    public void cancel(int taskId) {
      tasks.get(taskId).interrupt();
      return;
    }

    public void cancelAll() {
      for (Thread task : new ArrayList<>(tasks.values())) {
        task.interrupt();
      }
    }
  }

  public static class Logger implements com.nookure.staff.api.Logger {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("NookureStaff-Redis");

    @Override
    public void info(Component component) {
      logger.info(asString(component));
    }

    @Override
    public void warning(Component component) {
      logger.warn(asString(component));
    }

    @Override
    public void severe(Component component) {
      logger.error(asString(component));
    }

    @Override
    public void debug(Component component) {
      logger.debug(asString(component));
    }

    public String asString(@NotNull final Component component) {
      return PlainTextComponentSerializer.plainText().serialize(component);
    }
  }
}
