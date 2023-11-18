dependencies {
  compileOnly(libs.paperApi)
  compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
}

repositories {
  maven("https://repo.codemc.io/repository/nms/")
}

subprojects {
  dependencies {
    compileOnly(project(":MAStaff-NMS"))
    compileOnly(project(":MAStaff-API"))
    compileOnly(rootProject.libs.adventureApi)
    compileOnly(rootProject.libs.adventureLegacy)
  }

  repositories {
    maven("https://repo.codemc.io/repository/nms/")
  }
}