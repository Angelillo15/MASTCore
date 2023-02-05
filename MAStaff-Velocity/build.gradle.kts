plugins {
    java
    id("net.kyori.blossom") version "1.3.1"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

version = parent?.version ?: "unknown"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        content {
            includeGroup("com.velocitypowered")
        }
    }
    mavenCentral()
    maven("https://repo.alessiodp.com/releases/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
    implementation("net.byteflux:libby-velocity:1.1.5")
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
    compileOnly("com.github.Angelillo15:ConfigManager:1.4")
}

blossom {
    replaceTokenIn("src/main/java/es/angelillo15/mast/velocity/Constants.java")
    replaceToken("{version}", project.version)
}

