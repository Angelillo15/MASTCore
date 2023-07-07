import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("java")
    `maven-publish`
    id("net.kyori.blossom") version "1.3.1"
    id("org.ajoberstar.grgit") version "4.1.0"
}

group = "es.angelillo15"
version = parent?.version ?: "2.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.alessiodp.com/releases/")
    maven("https://jitpack.io")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly(libs.waterfall)
    compileOnly(libs.spigot)
    compileOnly(libs.placeholderApi)
    compileOnly(libs.eglow)
    compileOnly(libs.liblyBukkit)
    compileOnly(libs.bundles.invAPI)
    compileOnly(libs.configUpdater)
    compileOnly(libs.snakeYaml)
    compileOnly(libs.simpleYaml)
    compileOnly(libs.jedis)
    compileOnly(libs.hikariCP)
    compileOnly(libs.caffeine)
    compileOnly(libs.storm)
    compileOnly(libs.configManager)
    compileOnly(libs.adventureApi)
    compileOnly(libs.adventureBukkit)
    compileOnly(libs.adventureBungee)
    compileOnly(libs.miniMessage)
    compileOnly(libs.protocolLib)
}

blossom {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val current = LocalDateTime.now().format(formatter)


    replaceTokenIn("src/main/java/es/angelillo15/mast/api/Constants.java")
    replaceToken("{version}", project.version)
    replaceToken("{git-commit}",  grgit.head().abbreviatedId ?: "undefined")
    replaceToken("{git-user}", grgit.head().committer.name ?: "undefined")
    replaceToken("{git-date}", current ?: "undefined")
    replaceToken("{git-branch}", grgit.branch.current().name ?: "undefined")

    if (project.version.toString().endsWith("-SNAPSHOT") ||
            project.version.toString().endsWith("-DEV") ||
            project.version.toString().endsWith("-BETA") ||
            project.version.toString().endsWith("-ALPHA"))
    {
        replaceToken("false", "true")
    }

}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}