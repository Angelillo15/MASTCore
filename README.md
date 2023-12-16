# MAStaff GitHub Repository

This repository contains the source code for the MAStaff plugin.

## Installation

1. Download the latest release from
   the [releases page](https://www.spigotmc.org/resources/%E2%9C%A8-mastaff-staff-mode-staff-chat-bungee-spigot.105713/)
2. Place the downloaded jar file in your server's `plugins` folder
3. Restart your server

## Compile
Bash
```shell
MASTAFF_NMS=TRUE ./gradlew MAStaff-NMS:publishToMavenLocal shadowJar
```

CMD
```shell
set MASTAFF_NMS=TRUE
gradlew.bat MAStaff-NMS:publishToMavenLocal shadowJar
```

PowerShell
```shell
$env:MASTAFF_NMS=TRUE
.\gradlew.bat MAStaff-NMS:publishToMavenLocal shadowJar
```

[![Discord](https://discordapp.com/api/guilds/918181438879305748/widget.png)](https://discord.angelillo15.es) 