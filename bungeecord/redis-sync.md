---
description: Here you will learn all about how to config the server sync
---

# ⚖ Redis Sync

### When to use the Redis Sync ?

You should use the Redis Sync when we have 2 or more BungeeCord instances

### How to setup ?

In the Redis Config section

{% hint style="danger" %}
The serverID must be unique
{% endhint %}

```yaml
Redis:
  # Enable or disable the Redis synchronization.
  enabled: false
  # The host of your Redis server.
  host: "localhost"
  # The port of your Redis server.
  port: 6379
  # The password of your Redis server.
  password: ""
  # The database of your Redis server.
  database: 0
  # The timeout of your Redis server.
  timeout: 2000
  # The pool size of your Redis server.
  poolSize: 8
  # The prefix of your Redis server.
  prefix: "MAStaff"
  # The channel of your Redis server.
  channel: "MAStaff"
  # Identify the server.
  serverID: "p1"
```

Change enable from false -> true and config  the database with your credentials and restart the server! Thats all

&#x20;
