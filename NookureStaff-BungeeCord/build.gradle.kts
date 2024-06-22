plugins {
  alias(libs.plugins.pluginYmlBungee)
}

dependencies {
  implementation(project(":NookureStaff-API"))
  implementation(project(":NookureStaff-Common"))
  compileOnly(libs.bungeecord)
  implementation(libs.miniMessage)
  implementation(libs.adventureApi)
  implementation(libs.textLegacySerializer)
  implementation(libs.guice)
}

bungee {
  main = "com.nookure.staff.bungeecord.NookureStaff"
  author = "Angelillo15"
  version = rootProject.version.toString()
  description = "A staff plugin bridge by Nookure"
}

tasks.shadowJar {
  archiveFileName.set("NookureStaff-BungeeCord-${rootProject.version}.jar")
}