package com.nookure.staff.sync;

import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.JedisPool;

public abstract class RedisBaseTest {
  protected JedisPool jedisPool;

  @Container
  public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6-alpine"))
      .withExposedPorts(6379);

  @BeforeEach
  public void setUp() {
    String address = redis.getHost();
    Integer port = redis.getFirstMappedPort();

    jedisPool = new JedisPool(address, port);
  }
}
