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
    compileOnly(libs.spigot)
    compileOnly(libs.paperApi)
    compileOnly(libs.snakeYaml)
    compileOnly(libs.simpleYaml)
    compileOnly(libs.configManager)
    compileOnly(libs.hikariCP)
    compileOnly(libs.bundles.invAPI)
    compileOnly(libs.placeholderApi)
    compileOnly(libs.vault)
    compileOnly(libs.unirest)
    compileOnly(libs.unirest)
    implementation(libs.liblyBukkit)
    compileOnly(libs.paperLib)
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