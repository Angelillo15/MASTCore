plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version "8.1.1"
  kotlin("jvm") version "1.9.0"
}

dependencies {
  implementation(libs.bundles.invAPI)
  implementation(libs.liblyBukkit)
  implementation(libs.reflections)
  implementation(libs.paperLib)
  implementation(project(":MAStaff-API"))
  implementation(project(":MAStaff-Bukkit"))
  implementation(project(":MAStaff-Legacy"))
  implementation(project(":MAStaff-Vanish"))
  implementation(project(":MAStaff-PAPI"))
  implementation(project(":MAStaff-Glow"))
}

version = parent!!.version

tasks.shadowJar {
  archiveFileName.set("MAStaffLite-$version.jar")
  exclude("org/slf4j/")
  exclude("**/MAStaffLoader.class")
  exclude("**/CustomItemsLoaders.class")
  Relocation.registerAll()
  Relocation.relocations.forEach { (from, to) ->
    relocate(from, to)
  }
}

tasks.processResources {
  filesMatching("plugin.yml") {
    expand("version" to (version))
  }
}
