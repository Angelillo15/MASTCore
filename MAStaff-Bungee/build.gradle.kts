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
    compileOnly("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
    compileOnly("com.github.Angelillo15:ConfigManager:1.4")
    compileOnly("net.md-5:bungeecord-chat:1.16-R0.4")
    compileOnly("com.github.proxiodev.redisbungee:RedisBungee-Bungee:0.10.2")
    compileOnly("redis.clients:jedis:4.4.0-m2")
    compileOnly("com.konghq:unirest-java:3.11.09")
    implementation("net.byteflux:libby-bungee:1.1.5")
}

tasks.processResources {
    filesMatching("bungee.yml") {
        expand("version" to (parent?.version ?: project.version))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}