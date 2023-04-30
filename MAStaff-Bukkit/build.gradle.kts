plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.alessiodp.com/releases/")
}

dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(project(":MAStaff-Legacy"))
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
    compileOnly("com.github.Angelillo15:ConfigManager:1.4")
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")
    compileOnly("com.github.hamza-cskn.obliviate-invs:core:4.1.10")
    compileOnly("com.github.hamza-cskn.obliviate-invs:configurablegui:4.1.10")
    compileOnly("com.github.hamza-cskn.obliviate-invs:advancedslot:4.1.10")
    compileOnly("com.github.hamza-cskn.obliviate-invs:pagination:4.1.10")
    compileOnly("me.clip:placeholderapi:2.11.2")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("com.konghq:unirest-java:3.11.09")
    compileOnly("com.github.MrGraycat:Eglow:3.2.2-PRE2")
    implementation("net.byteflux:libby-bukkit:1.1.5")
    compileOnly("io.papermc:paperlib:1.0.7")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to (parent?.version ?: project.version))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}