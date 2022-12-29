plugins {
    id("java")
}

group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    implementation(project(":MAStaff-API"))
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to (parent?.version ?: project.version))
    }
}
