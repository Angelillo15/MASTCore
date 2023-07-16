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
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.alessiodp.com/releases/")
}

dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(libs.spigotLegacy)
}
