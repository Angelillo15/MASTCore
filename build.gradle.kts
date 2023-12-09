plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version "8.1.1"
  kotlin("jvm") version "1.9.0"
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "es.angelillo15"
version = "3.0.3"

tasks.shadowJar {
  Relocation.registerAll()
  Relocation.relocations.forEach { (from, to) ->
    relocate(from, to)
  }
}

dependencies {
  implementation(project(":MAStaff-API"))
  implementation(project(":MAStaff-Bukkit"))
  implementation(project(":MAStaff-Bungee"))
  implementation(project(":MAStaff-Legacy"))
  implementation(project(":MAStaff-Punishments"))
  implementation(project(":MAStaff-Vanish"))
  implementation(project(":MAStaff-PAPI"))
  implementation(project(":MAStaff-Glow"))
  implementation(project(":MAStaff-Velocity"))
  implementation(project(":MAStaff-Common"))
  implementation(libs.bundles.invAPI)
  implementation(libs.liblyBukkit)
  implementation(libs.reflections)
  implementation(libs.paperLib)
  implementation(libs.configurateGson)
  implementation(libs.configurateHocon)
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

tasks.build {
  dependsOn("shadowJar")
}

tasks.shadowJar {
  archiveFileName.set("MAStaff-" + project.version + ".jar")
  exclude("org/slf4j/")
}

allprojects {
  apply(plugin = "java")
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "com.github.johnrengelman.shadow")

  repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.alessiodp.com/releases/")
    maven("https://papermc.io/repo/repository/maven-releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://mvn.exceptionflug.de/repository/exceptionflug-public/")
    maven("https://repo.simplix.dev/repository/simplixsoft-public")
    maven("https://repo.codemc.io/repository/nms/")
  }

  tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  dependencies {
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    compileOnly("org.reflections:reflections:0.10.2")
    compileOnly(rootProject.libs.kotlin)
    compileOnly(rootProject.libs.caffeine)
    testImplementation("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(rootProject.libs.paperApi)
    testImplementation(rootProject.libs.guice)
    testImplementation(rootProject.libs.kotlin)
    testImplementation(rootProject.libs.caffeine)
    testImplementation(rootProject.libs.storm)
    compileOnly(rootProject.libs.guice)
  }

  kotlin {
    jvmToolchain(17);
  }

  tasks.test {
    useJUnitPlatform()
  }
}
