dependencies {
  implementation(libs.libbyVelocity)
  compileOnly(project(":MAStaff-API"))
  compileOnly(project(":MAStaff-Common"))
  compileOnly(libs.velocity)
  annotationProcessor(libs.velocity)
  compileOnly(libs.snakeYaml)
  compileOnly(libs.simpleYaml)
  compileOnly(libs.configManager)
  compileOnly(libs.bungeecordChat)
  compileOnly(libs.jedis)
  compileOnly(libs.unirest)
  compileOnly(libs.storm)
  compileOnly(libs.miniMessage)
  compileOnly(libs.adventureBungee)
  compileOnly(libs.adventureApi)
  compileOnly(libs.guice)
}

tasks.processResources {
  filesMatching("bungee.yml") {
    expand("version" to (parent?.version ?: project.version))
  }
}
