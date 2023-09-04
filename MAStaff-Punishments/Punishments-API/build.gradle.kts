plugins {
  id("java")
}

repositories {
  mavenCentral()
  maven("https://jitpack.io")
  maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
  compileOnly(libs.configManager)
  compileOnly(libs.snakeYaml)
  compileOnly(libs.simpleYaml)
  compileOnly(libs.waterfall)
  compileOnly(libs.storm)
  compileOnly(libs.adventureBungee)
  compileOnly(libs.miniMessage)
  compileOnly(libs.adventureApi)
}
