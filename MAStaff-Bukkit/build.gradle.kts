group = parent?.group ?: "es.angelillo15"
version = parent?.version ?: "undefined"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":MAStaff-API"))
    compileOnly(project(":MAStaff-Legacy"))
    compileOnly(project(":MAStaff-Vanish"))
    compileOnly(project(":MAStaff-PAPI"))
    compileOnly(project(":MAStaff-Glow"))
    compileOnly(libs.spigot)
    compileOnly(libs.paperApi)
    compileOnly(libs.snakeYaml)
    compileOnly(libs.simpleYaml)
    compileOnly(libs.configManager)
    compileOnly(libs.hikariCP)
    compileOnly(libs.bundles.invAPI)
    compileOnly(libs.placeholderApi)
    compileOnly(libs.vault)
    compileOnly(libs.unirest)
    compileOnly(libs.liblyBukkit)
    compileOnly(libs.paperLib)
    compileOnly(libs.miniMessage)
    compileOnly(libs.adventureBukkit)
    compileOnly(libs.adventureApi)
    compileOnly(libs.guice)
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to (parent?.version ?: project.version))
    }
}
