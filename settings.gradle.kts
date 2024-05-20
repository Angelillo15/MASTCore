rootProject.name = "NookureStaff"
include("NookureStaff-Paper")
include("NookureStaff-API")
include("NookureStaff-Velocity")
include("NookureStaff-Waterfall")
include("NookureStaff-Common")

pluginManagement {
  repositories {
    maven {
      url = uri("https://repo.kyngs.xyz/gradle-plugins")
    }
    gradlePluginPortal()
  }
}