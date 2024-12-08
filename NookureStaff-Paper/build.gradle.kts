import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
  alias(libs.plugins.pluginYmlPaper)
  alias(libs.plugins.pluginYmlBukkit)
}

group = "com.nookure.staff"

dependencies {
  implementation(project(":NookureStaff-API"))
  implementation(project(":NookureStaff-Common"))
  compileOnly(libs.paperApi)
  compileOnly(libs.superVanish)
  compileOnly(libs.libbyPaper)
  compileOnly(libs.configurateYaml)
  compileOnly(libs.jedis)
  compileOnly(libs.placeholderApi)
  compileOnly(libs.lucko.commodore)
  compileOnly(libs.nookure.core.inventory)
  compileOnly(libs.lucko.luckperms)
  bukkitLibrary(libs.guice)
  bukkitLibrary(libs.google.guice.assistedinject)
  paperLibrary(libs.guice)

  /* Start of MockBukkit stuff */
  testImplementation(libs.mockBukkit)
  testImplementation(libs.guice)
  testImplementation(libs.google.guice.assistedinject)
  testImplementation(libs.libbyPaper)
  testImplementation(libs.configurateYaml)
  testImplementation(libs.nookure.core.inventory)
  testImplementation(libs.jedis)
  testImplementation(libs.libbyPaper)
  testImplementation(libs.liblyBukkit)
  testImplementation(libs.adventureApi)
  testImplementation(libs.miniMessage)
  testImplementation(libs.adventureBukkit)
  testImplementation(libs.ebean)
  testImplementation(libs.hikariCP)

  testImplementation("org.xerial:sqlite-jdbc:3.46.0.0")
  testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  /* End of MockBukkit stuff */
}

bukkit {
  name = "NookureStaff"
  version = rootProject.version.toString()
  apiVersion = "1.16"
  description = "A staff plugin by Nookure"
  website = "https://angelillo15.es/"
  authors = listOf("Angelillo15")
  main = "com.nookure.staff.paper.bootstrap.StaffBootstrapper"
  softDepend = listOf("PlaceholderAPI", "LuckPerms", "SuperVanish", "PremiumVanish")
}

paper {
  name = "NookureStaff"
  version = rootProject.version.toString()
  description = "A staff plugin by Nookure"
  apiVersion = "1.19"
  website = "https://angelillo15.es/"
  authors = listOf("Angelillo15")
  loader = "com.nookure.staff.paper.bootstrap.StaffPaperPluginLoader"
  main = "com.nookure.staff.paper.bootstrap.StaffBootstrapper"
  serverDependencies {
    register("PlaceholderAPI") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
      joinClasspath = true
    }
    register("SuperVanish") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
      joinClasspath = true
    }
    register("PremiumVanish") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
      joinClasspath = true
    }
    register("LuckPerms") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
      joinClasspath = true
    }
  }
}

tasks.test {
  useJUnitPlatform()
}
