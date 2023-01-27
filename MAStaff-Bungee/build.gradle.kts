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
    compileOnly("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    implementation(project(":MAStaff-Common"))
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
    compileOnly("com.github.Angelillo15:ConfigManager:1.4")
    compileOnly("net.md-5:bungeecord-chat:1.16-R0.4")
    implementation("net.byteflux:libby-bungee:1.1.5")
    implementation(project(":MAStaff-API"))
}

tasks.processResources {
    filesMatching("bungee.yml") {
        expand("version" to (parent?.version ?: project.version))
    }
}