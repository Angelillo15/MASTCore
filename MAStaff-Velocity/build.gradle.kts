dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
    compileOnly(libs.snakeYaml)
    compileOnly(libs.simpleYaml)
    compileOnly(libs.configManager)
    compileOnly(libs.bungeecordChat)
    compileOnly(libs.jedis)
    compileOnly(libs.unirest)
    implementation(libs.libbyVelocity)
    compileOnly(libs.storm)
    compileOnly(libs.miniMessage)
    compileOnly(libs.adventureBungee)
    compileOnly(libs.adventureApi)
}

tasks.processResources {
    filesMatching("bungee.yml") {
        expand("version" to (parent?.version ?: project.version))
    }
}
