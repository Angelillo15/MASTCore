package com.nookure.staff.sync;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;

@Testcontainers
public class RedisBrokerTest extends RedisBaseTest {
  @Test
  public void testSimplePutAndGet() {
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.set("key", "value");
      String value = jedis.get("key");
      assert value.equals("value");
    }
  }
}
