
![Nookure Staff](https://i.imgur.com/3ngZgnP.png)

# Nookure Staff

### Introducing NookureStaff (formerly MAStaff)
NookureStaff is the most advanced staff utilities plugin for Minecraft servers. Its comprehensive features, extensive customization options, and remarkable flexibility make it suitable for any type of Minecraft server, whether a small standalone server or a massive multi-proxy network.

# Installation
Download the plugin from an official source like [BuiltByBit](https://builtbybit.com/resources/nookurestaff-staffmode-utils.25460/) or [Polymart](https://polymart.org/resource/nookurestaff-staffmode-amp-utils.3051)

Then drag and drop the jar file to your server plugins config, to customize the plugin and its configs please visit the [documentation](https://docs.nookure.com/nkstaff/what-is-nookurestaff.html)


# Development
To build the project you need to have gradle and JDK 17

First you need to clone the repo

```shell
git clone https://github.com/Nookure/NookureStaff
```

Then you need to build the project
```shell    
gradlew shadowJar
```

If you have modified a model or create one you will need to generate a migration file
```sh
gradlew NookureStaff-Common:generateMigration
```