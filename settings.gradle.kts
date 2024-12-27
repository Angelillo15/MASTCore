rootProject.name = "NookureStaff"
include("NookureStaff-Paper")
include("NookureStaff-API")
include("NookureStaff-Velocity")
include("NookureStaff-Common")

pluginManagement {
  repositories {
    maven {
      url = uri("https://repo.kyngs.xyz/gradle-plugins")
    }
    gradlePluginPortal()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}