---
description: Plugin configuration for bukkit/paper/folia/purpur
---

# 🖇 Config

* If you modify something remeber to reload the plugin with **/mast reload**

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
# This is the Config file for MAStaff Spigot,
# where you can modify and customize the plugin.
#
# Remember to join my Discord server if you need help:
# https://discord.nookure.com/
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
  # Teleport to the previous location when the player leaves the staff mode.
  teleportBack: true
  # Disable the staff mode on exit.
  disableStaffModeOnExit: false
  # Silent chest opening.
  silentChestOpening: true
# Database configuration for the plugin. With the Bungee version, you must use MariaDB or MySQL.
Database:
  # The type of database you want to use, you can choose between SQLite or MySQL.
  # The MySQL driver also works with MariaDB
  type: 'SQLite'
  # Your database host IP address.
  host: '127.0.0.1'
  # Your database host connection port. Default for MariaDB and MySQL is 3306.
  port: 3306
  # Name of your database. A database server can contain multiple databases.
  database: 'mastaff'
  # Your database host username.
  user: 'mastaff'
  # Your database host password.
  password: 'mastaff'
```
