---
description: Plugin configuration for bungeecord/waterfall
---

# 🖇️ Config

{% hint style="danger" %}
**This project has reached end of life and is no longer maintained.**

**We recommend you transition to** [**NookureStaff**](https://builtbybit.com/resources/nookurestaff-staffmode-utils.25460/)**, it's free**
{% endhint %}

If you modify something remeber to reload the plugin with **/mastb reload**

```yaml
#
#  ███▄ ▄███▓ ▄▄▄        ██████ ▄▄▄█████▓ ▄▄▄        █████▒ █████▒
#  ▓██▒▀█▀ ██▒▒████▄    ▒██    ▒ ▓  ██▒ ▓▒▒████▄    ▓██   ▒▓██   ▒
#  ▓██    ▓██░▒██  ▀█▄  ░ ▓██▄   ▒ ▓██░ ▒░▒██  ▀█▄  ▒████ ░▒████ ░
#  ▒██    ▒██ ░██▄▄▄▄██   ▒   ██▒░ ▓██▓ ░ ░██▄▄▄▄██ ░▓█▒  ░░▓█▒  ░
#  ▒██▒   ░██▒ ▓█   ▓██▒▒██████▒▒  ▒██▒ ░  ▓█   ▓██▒░▒█░   ░▒█░
#  ░ ▒░   ░  ░ ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░  ▒ ░░    ▒▒   ▓▒█░ ▒ ░    ▒ ░
#  ░  ░      ░  ▒   ▒▒ ░░ ░▒  ░ ░    ░      ▒   ▒▒ ░ ░      ░
#  ░      ░     ░   ▒   ░  ░  ░    ░        ░   ▒    ░ ░    ░ ░
#  ░         ░  ░      ░                 ░  ░
#
# This is the Config file for MAStaff Bungee,
# where you can modify and customize the plugin.
#
# Remember to join my Discord server if you need help:
# https://discord.angelillo15.es/
#
# General configuration for the plugin.
#
Config:
  # The language file you want to use for the plugin messages.
  # Included locales: english.yml and spanish.yml
  language: "english.yml"
  # Enable or disable the debug mode.
  # If enabled, the plugin will send more information about the plugin status.
  debug: false
Helpop:
  # Cooldown in seconds
  cooldown: 30
#
# Server synchronization configuration for the plugin.
# This is used to synchronize the staff mode between bungeecord servers.
# If you don't want to use this feature, you can disable it.
# This feature requires a Redis server and Redis Bungee.
#
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
