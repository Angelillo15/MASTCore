---
description: MAStaff GitHub documentation
layout:
  title:
    visible: true
  description:
    visible: true
  tableOfContents:
    visible: true
  outline:
    visible: true
  pagination:
    visible: true
---

# 🚀 Information

## Do I have the source code when I bought the plugin?

Yes, you can request the acces in the official [discord](https://discord.nookure.com/)



## Branchs

> MAStaff use git branching, this is a feature that allows to developers to diverge from the production version of the code, that means that whe can have different versions of the code without break production branch



<table><thead><tr><th width="258">Branch name</th><th>Info</th></tr></thead><tbody><tr><td>2.x</td><td>Releases</td></tr><tr><td>feature/&#x3C;feature></td><td>Feature in progress</td></tr><tr><td>fix/&#x3C;fix></td><td>Fix in progress</td></tr></tbody></table>

## Modules

MAStaff project have this structure

```
|-- MAStaff
|   |-- MAStaff-API
|   |-- MAStaff-Bukkit
|   |-- MAStaff-Bungee
|   |-- MAStaff-Common
|   |-- MAStaff-Glow
|   |-- MAStaff-Legacy
|   |-- MAStaff-Lite
|   |-- MAStaff-NMS
|   |   |-- NMS-1.12.2_R0
|   |   |-- NMS-1.16.5_R0
|   |   |-- NMS-1.17.1_R0
|   |   |-- NMS-1.18.2_R0
|   |   |-- NMS-1.19.4_R0
|   |   |-- NMS-1.20.1_R0
|   |   |-- NMS-1.8.8_R0
|   |   |-- build
|   |   |-- build.gradle.kts
|   |   `-- src
|   |-- MAStaff-PAPI
|   |-- MAStaff-Punishments
|   |   |-- Punishments-API
|   |   |-- Punishments-Bukkit
|   |   |-- Punishments-Bungee
|   |   |-- Punishments-Velocity
|   |   |-- build
|   |   |-- build.gradle.kts
|   |   `-- src
|   |-- MAStaff-Vanish
|   |-- MAStaff-Velocity
|   |-- README.md
|   |-- build
|   |-- build.gradle.kts
|   |-- gradle
|   |-- gradlew
|   |-- gradlew.bat
|   `-- settings.gradle.kts
```

#### MAStaff-API

This module should be accesible from all the project

#### MAStaff-Bukkit

This module have all bukkit version releated files

#### MAStaff-Bungee

This module have all BungeeCord releated files

#### MAStaff-Common

This module have common listeners and commands between BungeeCord, Velocity and Bukkit

#### MAStaff-Glow

This module is for bukkit, it haves all the glow extension releated files

#### MAStaff-Legacy

This module have listeners for version like < 1.12.2&#x20;

#### MAStaff-Lite

This is the lite version of MAStaff

#### MAStaff-NMS

This module contains all supported versions by MAStaff

#### MAStaff-PAPI

This module contains all placeholders for MAStaff

#### MAStaff-Punishments

This module contains the code of the Punishments system currently in development

#### MAStaff-Vanish

This module contains vanish listeners and vanish player

#### MAStaff-Velocity

This module have all velocity version releated files
