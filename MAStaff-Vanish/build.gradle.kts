plugins {
  id("java")
}

group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
  compileOnly(project(":MAStaff-API"))
  compileOnly(libs.spigot)
  compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
}
