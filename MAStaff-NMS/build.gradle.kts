import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  `maven-publish`
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
  compileOnly(libs.paperApi)
  compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
  implementation(project(":MAStaff-NMS:NMS-1.20.2_R0", "reobf"))
  implementation(project(":MAStaff-NMS:NMS-1.20.1_R0", "reobf"))
  implementation(project(":MAStaff-NMS:NMS-1.19.4_R0", "reobf"))
  implementation(project(":MAStaff-NMS:NMS-1.18.2_R0", "reobf"))
  implementation(project(":MAStaff-NMS:NMS-1.17.1_R0", "reobf"))
  implementation(project(":MAStaff-NMS:NMS-1.16.5_R0"))
  implementation(project(":MAStaff-NMS:NMS-1.12.2_R0"))
  implementation(project(":MAStaff-NMS:NMS-1.8.8_R0"))
}

repositories {
  maven("https://repo.codemc.io/repository/nms/")
}

subprojects {
  dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(rootProject.libs.adventureApi)
    compileOnly(rootProject.libs.adventureLegacy)
  }

  repositories {
    maven("https://repo.codemc.io/repository/nms/")
  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "com.nookure.mast"
      artifactId = "MAStaff-NMS"
      version = "1.20.2"
      artifact(tasks.withType(ShadowJar::class.java).getByName("shadowJar").archiveFile)
    }
  }
}

tasks.shadowJar {
  Relocation.registerAll()
  Relocation.relocations.forEach { (from, to) ->
    relocate(from, to)
  }
  exclude("META-INF/maven/**")
}