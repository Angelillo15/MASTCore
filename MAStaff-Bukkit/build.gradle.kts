group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
  mavenCentral()
}

val compileOnlyApi: Configuration by configurations.creating
configurations["compileClasspath"].extendsFrom(compileOnlyApi)
configurations["apiElements"].extendsFrom(compileOnlyApi)

dependencies {
  compileOnly(project(":MAStaff-API"))
  compileOnly(project(":MAStaff-Legacy"))
  compileOnly(project(":MAStaff-Vanish"))
  compileOnly(project(":MAStaff-PAPI"))
  compileOnly(project(":MAStaff-Glow"))
  compileOnlyApi(libs.spigot)
  compileOnlyApi(libs.paperApi)
  compileOnlyApi(libs.snakeYaml)
  compileOnlyApi(libs.simpleYaml)
  compileOnlyApi(libs.configManager)
  compileOnlyApi(libs.hikariCP)
  compileOnlyApi(libs.bundles.invAPI)
  compileOnlyApi(libs.placeholderApi)
  compileOnlyApi(libs.vault)
  compileOnlyApi(libs.unirest)
  compileOnlyApi(libs.liblyBukkit)
  compileOnlyApi(libs.paperLib)
  compileOnlyApi(libs.miniMessage)
  compileOnlyApi(libs.adventureBukkit)
  compileOnlyApi(libs.adventureApi)
  compileOnlyApi(libs.guice)
}

tasks.processResources {
  filesMatching("plugin.yml") {
    expand("version" to (parent?.version ?: project.version))
  }
}
