plugins {
  alias(libs.plugins.pluginYmlBungee)
}

dependencies {
  implementation(project(":NookureStaff-API"))
  implementation(project(":NookureStaff-Common"))
  compileOnly(libs.waterfall)
  implementation(libs.miniMessage)
  implementation(libs.adventureApi)
  implementation(libs.textLegacySerializer)
}

bungee {
  main = "com.nookure.staff.waterfall.NookureStaff"
  author = "Angelillo15"
  version = rootProject.version.toString()
  description = "A staff plugin bridge by Nookure"
}

tasks.shadowJar {
  archiveFileName.set("NookureStaff-Waterfall-${rootProject.version}.jar")
}