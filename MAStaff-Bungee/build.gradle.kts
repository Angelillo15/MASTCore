plugins {
    id("java")
}

group = "es.angelillo15"
version = "2.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.alessiodp.com/releases/")

}

dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(project(":MAStaff-Punishments:Punishments-Bungee"))
    compileOnly(libs.waterfall)
    compileOnly(libs.snakeYaml)
    compileOnly(libs.simpleYaml)
    compileOnly(libs.configManager)
    compileOnly(libs.bungeecordChat)
    compileOnly(libs.jedis)
    compileOnly(libs.unirest)
    implementation(libs.liblyBungee)
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
