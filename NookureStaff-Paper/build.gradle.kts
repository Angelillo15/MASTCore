plugins {
  alias(libs.plugins.pluginYmlPaper)
  alias(libs.plugins.pluginYmlBukkit)
}

group = "com.nookure.staff"

dependencies {
  implementation(project(":NookureStaff-API"))
  implementation(project(":NookureStaff-Common"))
  compileOnly(libs.paperApi)
  compileOnly(libs.libbyPaper)
  compileOnly(libs.configurateYaml)
  compileOnly(libs.jedis)
}

bukkit {
  name = "NookureStaff"
  version = rootProject.version.toString()
  apiVersion = "1.16"
  description = "A staff plugin by Nookure"
  website = "https://angelillo15.es/"
  authors = listOf("Angelillo15")
  main = "com.nookure.staff.bukkit.bootstrap.StaffBootstrapper"
}

paper {
  name = "NookureStaff"
  version = rootProject.version.toString()
  description = "A staff plugin by Nookure"
  apiVersion = "1.19"
  website = "https://angelillo15.es/"
  authors = listOf("Angelillo15")
  main = "com.nookure.staff.paper.bootstrap.StaffBootstrapper"
}