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
  relocate("es.angelillo15.configmanager", "es.angelillo15.mast.libs.config.manager")
  relocate("org.yaml.snakeyaml", "es.angelillo15.mast.libs.snakeyaml")
  relocate("org.simpleyaml", "es.angelillo15.mast.libs.simpleyaml")
  relocate("es.angelillo15.glow", "es.angelillo15.mast.libs.glow")
  relocate("mc.obliviate", "es.angelillo15.mast.libs.obliviate")
  relocate("com.zaxxer.hikari", "es.angelillo15.mast.libs.hikari")
  relocate("com.google.common", "es.angelillo15.mast.libs.google.common")
  relocate("com.google.gson", "es.angelillo15.mast.libs.google.gson")
  relocate("com.google.thirdparty", "es.angelillo15.mast.libs.google.thirdparty")
  relocate("com.google.errorprone", "es.angelillo15.mast.libs.google.errorprone")
  relocate("com.google.j2objc", "es.angelillo15.mast.libs.google.j2objc")
  relocate("javax.annotation", "es.angelillo15.mast.libs.javax.annotation")
  relocate("org.checkerframework", "es.angelillo15.mast.libs.checkerframework")
  relocate("net.byteflux.libby", "es.angelillo15.mast.libs.libby")
  relocate("ru.vyarus.yaml.updater", "es.angelillo15.mast.libs.yaml-config-updater")
  relocate("kong.unirest", "es.angelillo15.mast.libs.unirest")
  relocate("org.apache.http", "es.angelillo15.mast.libs.apache.http")
  relocate("org.apache.commons.logging", "es.angelillo15.mast.libs.commons-logging")
  relocate("org.reflections", "es.angelillo15.mast.libs.reflections")
  relocate("redis.clients.jedis", "es.angelillo15.mast.libs.jedis")
  relocate("io.papermc.lib", "es.angelillo15.mast.libs.paperlib")
  relocate("com.github.benmanes.caffeine", "es.angelillo15.mast.libs.caffeine")
  relocate("com.craftmend.storm", "es.angelillo15.mast.libs.storm")
  relocate("javassist", "es.angelillo15.mast.libs.javassist")
}

tasks.processResources {
  filesMatching("plugin.yml") {
    expand("version" to (version))
  }
}
